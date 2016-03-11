//
//  NotificationEx.m
//  FunChat1
//
//  Created by 胡占静 on 14-12-20.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import "NotificationEx.h"

@implementation NotificationEx

- (NotificationEx *)initWithName:(NSString *)name userInfo:(NSDictionary *)userInfo{
    self = [super init];
    if (self) {
        self.name = name;
        self.userInfo = userInfo;
        self.handledObservers = [NSMutableArray array];
    }
    return self;
}

- (void)addHandledObserver: (id) observer {
    [_handledObservers addObject:observer];
}

- (BOOL)isHandledForObserver: (id) observer {
    return [_handledObservers containsObject:observer];
}

@end
