//
//  CommandResult.h
//  FunChat1
//
//  Created by 胡占静 on 14-12-11.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "TalentException.h"
@class Command;

@interface CommandResult : NSObject

@property (nonatomic, strong) Command *command;
@property (nonatomic)NSInteger result;
@property (nonatomic, strong)TalentException *exception;
@property (nonatomic, strong)id returnObject;

- (CommandResult *)initWithResult:(NSInteger)result exception:(TalentException *)exception returnObject:(id)returnObject;

@end
