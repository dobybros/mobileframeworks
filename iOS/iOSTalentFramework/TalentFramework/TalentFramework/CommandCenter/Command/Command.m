//
//  Command.m
//  FunChat1
//
//  Created by 胡占静 on 14-12-11.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import "Command.h"
#import "CommandCenter.h"
#import "TalentException.h"

@implementation Command

- (Command *)initWithModule:(NSString *)module method:(SEL)method arguments:(id)firstObj, ...{
    self = [super init];
    if (self) {
        
        _shouldContinueWhenAppEntersBackground = NO;
        
        _enableCommandBefore = YES;
        _enableInterceptor = YES;
        _enableCommandAfter = YES;
        
        @try {
            va_list args;
            va_start(args, firstObj);
            _arguments = [[NSMutableArray alloc] initWithObjects:firstObj, nil];
            id obj;
            if (_arguments.count > 0) {
                while ((obj = va_arg(args, id)) != nil) {
                    [_arguments addObject:obj];
                }
            }
            va_end(args);
            _method = method;
            _moduleName = module;
        }
        @catch (NSException *exception) {
            TalentException *e;
            if(![exception isKindOfClass:[TalentException class]]) {
                e = [[TalentException alloc] initWithName:exception.name reason:exception.reason i18nKey:NSLocalizedString(@"Unknown error", nil) userInfo:exception.userInfo];
            } else {
                e = (TalentException *)exception;
            }
            @throw;
        }
        @finally {
            
        }
    }
    return self;
}

-(id)execute{
    return [[CommandCenter singleCommandCenter] executeCommandWithoutIntercept:self];
}

@end
