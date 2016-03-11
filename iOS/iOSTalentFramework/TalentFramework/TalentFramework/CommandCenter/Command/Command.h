//
//  Command.h
//  FunChat1
//
//  Created by 胡占静 on 14-12-11.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "CommandResult.h"
#import "Module.h"



@interface Command : NSObject

@property (nonatomic)SEL method;
@property (nonatomic, strong)NSMutableArray *arguments;
@property (nonatomic, strong)NSString *moduleName;
@property (nonatomic, strong) id userInfo;
@property (nonatomic) BOOL shouldContinueWhenAppEntersBackground;
@property (nonatomic) BOOL enableCommandBefore;
@property (nonatomic) BOOL enableInterceptor;
@property (nonatomic) BOOL enableCommandAfter;

- (Command *)initWithModule:(NSString *)module method:(SEL)method arguments:(id)firstObj, ...;

- (id)execute;

@end
