//
//  CommandResult.m
//  FunChat1
//
//  Created by 胡占静 on 14-12-11.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import "CommandResult.h"


@implementation CommandResult

- (CommandResult *)initWithResult:(NSInteger)result exception:(TalentException *)exception returnObject:(id)returnObject{
    self = [super init];
    if (self) {
        _result = result;
        _exception = exception;
        _returnObject = returnObject;
    }
    return self;
}

@end
