//
//  MethodInterceptor.h
//  FunChat1
//
//  Created by 胡占静 on 14-12-9.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "Command.h"

@protocol MethodInterceptor <NSObject>

- (id)methodInvocation:(Command *)command;

@end

/*
- (id)methodInvocation:(Command *)command\ {
    //can find object in database or not?
    //if yes
    return localId;
    //if no
    id returnObj = [command execute];
    //save return obj to database.
    return returnObj;
}
*/