//
//  CommandCenter.h
//  FunChat1
//
//  Created by 胡占静 on 14-12-9.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Command.h"
#import "CommandResult.h"
#import "MethodInterceptor.h"
#import "TalentException.h"
#import "Module.h"

//commantResult
#define COMMANT_RESULT_FAILURE 0
#define COMMANT_RESULT_SUCCESS 1



@protocol CommandDelegate <NSObject>

- (void)excuteCommandResult:(CommandResult *)commandResult;

@end

@protocol CommandAfterDelegate <NSObject>

- (CommandResult *)afterResult:(CommandResult *)commandResult forCommand: (Command *) command;

@end

@protocol CommandBeforeDelegate <NSObject>

- (CommandResult *)beforeCommand:(Command *) command;

@end

@interface CommandCenter : NSObject

@property (nonatomic, strong)NSMutableDictionary *moduleMap;
@property (nonatomic, strong)NSMutableDictionary *interceptorMap;
@property (nonatomic, strong)id<CommandBeforeDelegate> beforeDelegate;
@property (nonatomic, strong)id<CommandAfterDelegate> afterDelegate;
@property (nonatomic, strong)NSOperationQueue *queue;

+ (CommandCenter *)singleCommandCenter;
- (CommandResult *)executeCommandSync:(Command *)command;
- (void)sendCommandAsync:(Command *)command commandListener:(id<CommandDelegate>)commandListener;
- (void)sendCommandAsync:(Command *)command commandResultBlock:(void(^)(CommandResult *commandResult))commandResultBlock;
- (void)registerModule:(NSString *)moduleName module:(Module *)module;
- (void)unregisterModule:(NSString *)moduleName;
- (Module *)getModule: (NSString *)moduleName;

-(id)executeCommandWithoutIntercept:(Command *)command;
@end
