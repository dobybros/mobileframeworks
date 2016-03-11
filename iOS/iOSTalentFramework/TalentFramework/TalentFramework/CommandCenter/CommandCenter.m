//
//  CommandCenter.m
//  FunChat1
//
//  Created by 胡占静 on 14-12-9.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import "CommandCenter.h"
#import <objc/message.h>
#import "Module.h"
#import "TalentConstants.h"

@interface FaultMethodInterceptor : NSObject
@end
@implementation FaultMethodInterceptor
@end


@interface NSObject (PerformSelWithArray)
+(id) performSelectorOnInstance:(id)instance selector:(SEL)selector arguments:(NSArray *)arguments;
@end

@implementation NSObject (PerformSelWithArray)
+(id) performSelectorOnInstance:(id)instance selector:(SEL)selector arguments:(NSArray *)arguments
{
    NSMethodSignature *signature = [instance methodSignatureForSelector:selector];
    NSInvocation *invocation = [NSInvocation invocationWithMethodSignature:signature];
    [invocation setTarget:instance];
    [invocation setSelector:selector];
    
    for(int i=0; i<[arguments count]; i++)
    {
        id arg = [arguments objectAtIndex:i];
        if ([arg isKindOfClass:[NSNull class]]) {
            arg = nil;
        }
        [invocation setArgument:&arg atIndex:i+2]; // The first two arguments are the hidden arguments self and _cmd
    }
    
    [invocation invoke]; // Invoke the selector
    void *result;
    
    NSUInteger length = [[invocation methodSignature] methodReturnLength];
    
    if (length == 0) {
        return nil;
    } else {
        [invocation getReturnValue:&result];
        return (__bridge id)result;
    }
}

@end

static FaultMethodInterceptor *fault;
@implementation CommandCenter

static CommandCenter *commandCenter;
+ (CommandCenter *)singleCommandCenter
{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        commandCenter = [[self alloc] init];
    });
    return commandCenter;
}

-(instancetype)init
{
    self = [super init];
    if (self) {
        _moduleMap = [NSMutableDictionary dictionary];
        _interceptorMap = [NSMutableDictionary dictionary];
        fault = [[FaultMethodInterceptor alloc] init];
        _queue = [[NSOperationQueue alloc] init];
        [_queue setMaxConcurrentOperationCount:200];
    }
    return self;
}
-(id)executeCommandWithoutIntercept:(Command *)command
{
    id module = [_moduleMap objectForKey:command.moduleName];
    if(module == nil) {
        module = [[NSClassFromString(command.moduleName) alloc]init];
        [self registerModule:command.moduleName module:module];
    }
    
//    id returnObj = invokeSelector(module, command.method, command.arguments);
    id returnObj = [NSObject performSelectorOnInstance:module selector:command.method arguments:command.arguments];
    return returnObj;
}


id invokeSelector(id object, SEL selector, NSArray *arguments)
{
    NSMethodSignature *signature = [object methodSignatureForSelector:selector];
    NSInvocation *invocation = [NSInvocation invocationWithMethodSignature:signature];
    [invocation setTarget:object];
    [invocation setSelector:selector];
    
    for(int i=0; i<[arguments count]; i++)
    {
        id arg = [arguments objectAtIndex:i];
        [invocation setArgument:&arg atIndex:i+2]; // The first two arguments are the hidden arguments self and _cmd
    }
    
    [invocation invoke]; // Invoke the selector
    void *result;
    [invocation getReturnValue:&result];
    return (__bridge id)result;
}

-(CommandResult *)executeCommandSync:(Command *)command
{
    @try {
        if (command.enableCommandBefore) {
            if([self.beforeDelegate respondsToSelector:@selector(beforeCommand:)]) {
                CommandResult *result = [self.beforeDelegate beforeCommand:command];
                if(result != nil) {
                    return result;
                }
            }
        }
        
        if (fault == nil) {
            fault = [[FaultMethodInterceptor alloc] init];
        }
        NSString *interceptorKey = [self getInterceptorKey:command];
        id interceptor = [_interceptorMap objectForKey:interceptorKey];
        if(interceptor == nil) {
            @synchronized(_interceptorMap) {
                NSMutableString *intercepterClassName = [interceptorKey mutableCopy];
                [intercepterClassName appendString:@"Interceptor"];
                Class class = NSClassFromString(intercepterClassName);
                if(class != nil) {
                    interceptor = [[class alloc]init];
                    if([interceptor respondsToSelector:@selector(methodInvocation:)]) {  //可检查是否是其代理
                        [_interceptorMap setValue:interceptor forKey:interceptorKey];
                    } else {
                        interceptor = nil;
                    }
                }
                if(interceptor == nil) {
                    interceptor = fault;
                    [_interceptorMap setValue:interceptor forKey:interceptorKey];
                }
            }
        }
        CommandResult *result = nil;
        if(command.enableInterceptor && interceptor != nil && interceptor != fault) {
            id returnObj = [interceptor methodInvocation:command];
            result = [[CommandResult alloc] initWithResult:COMMANT_RESULT_SUCCESS exception:nil returnObject:returnObj];
        } else {
            id returnObj = [self executeCommandWithoutIntercept: command];
            result = [[CommandResult alloc] initWithResult:COMMANT_RESULT_SUCCESS exception:nil returnObject:returnObj];
        }
        result.command = command;
        if(command.enableCommandAfter && [self.afterDelegate respondsToSelector:@selector(afterResult:forCommand:)]) {
            CommandResult *newResult = [self.afterDelegate afterResult:result forCommand:command];
            if(newResult != nil) {
                newResult.command = command;
                return newResult;
            }
        }
        return result;
    }
    @catch (NSException *exception) {
        TalentException *e;
        if(![exception isKindOfClass:[TalentException class]]) {
            e = [[TalentException alloc] initWithName:exception.name reason:exception.reason i18nKey:MESSAGE_COMMANDCENTER_ERROR_UNKNOWN userInfo:exception.userInfo];
        } else {
            e = (TalentException *)exception;
        }
        if(command.enableCommandAfter && [self.afterDelegate respondsToSelector:@selector(afterResult:forCommand:)]) {
            CommandResult *newResult = [self.afterDelegate afterResult:[[CommandResult alloc] initWithResult:COMMANT_RESULT_FAILURE exception:e returnObject:nil] forCommand:command];
            if(newResult != nil) {
                newResult.command = command;
                return newResult;
            }
        }
        CommandResult *result = [[CommandResult alloc] initWithResult:0 exception:e returnObject:nil];
        result.command = command;
        return result;
    }
    @finally {
        
    }
    
}

-(NSString *)getInterceptorKey:(Command *)command
{
    NSMutableString *interceptor = [command.moduleName mutableCopy];
    NSString *method = NSStringFromSelector(command.method);
    NSMutableString *method1 = [[NSMutableString alloc] init];
    NSArray *methodArr = [method componentsSeparatedByString:@":"];
    for (NSString *str in methodArr) {
        NSMutableString *str1 = [str mutableCopy];
        if (![str1 isEqualToString:@""]) {
            [str1 replaceCharactersInRange:NSMakeRange(0, 1) withString:[[str1 substringToIndex:1] capitalizedString]];
            [method1 appendString:str1];
        }
    }
    [interceptor appendString:method1];
    return interceptor;
}

-(void)sendCommandAsync:(Command *)command commandListener:(id<CommandDelegate>)commandListene
{
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(queue, ^{
        CommandResult *commmandresult = [self executeCommandSync:command];
        if (commmandresult) {
            if ([commandListene respondsToSelector:@selector(excuteCommandResult:)]) {
                [commandListene excuteCommandResult:commmandresult];
            }
        }
    });
//    NSBlockOperation *op = [NSBlockOperation blockOperationWithBlock:^{
//        CommandResult *commmandresult = [self executeCommandSync:command];
//        if ([commandListene respondsToSelector:@selector(excuteCommandResult:)]) {
//            [commandListene excuteCommandResult:commmandresult];
//        }
//    }];
//    [_queue addOperation:op];
//    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
//    dispatch_async(queue, ^{
//        CommandResult *commmandresult = [self executeCommandSync:command];
//        if ([commandListene respondsToSelector:@selector(excuteCommandResult:)]) {
//            [commandListene excuteCommandResult:commmandresult];
//        }
//    });
}

- (void)sendCommandAsync:(Command *)command commandResultBlock:(void(^)(CommandResult *commandResult))commandResultBlock {
    dispatch_queue_t queue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(queue, ^{
        CommandResult *commmandresult = [self executeCommandSync:command];
        if (commmandresult) {
            commandResultBlock(commmandresult);
            
        }
    });
}

-(void)registerModule:(NSString *)moduleName module:(Module *)module
{
    if (moduleName != nil && moduleName != nil && ![moduleName isEqualToString:@""]) {
        [_moduleMap setValue:module forKey:moduleName];
    }
}

- (Module *)getModule: (NSString *)moduleName
{
    if(_moduleMap) {
        return [_moduleMap objectForKey:moduleName];
    }
    return nil;
}

- (void)unregisterModule:(NSString *)moduleName
{
    if (moduleName) {
        if ([_moduleMap valueForKey:moduleName]) {
            [_moduleMap removeObjectForKey:moduleName];
        }
    }
}

@end
