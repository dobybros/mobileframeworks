//
//  UINotification.m
//  TalentFramework
//
//  Created by 胡占静 on 16/2/4.
//  Copyright © 2016年 jianming. All rights reserved.
//

#import "UINotification.h"

@implementation UINotification

- (instancetype) initWithKeys: (NSArray *)keys {
    self = [super init];
    if (self) {
        self.keys = keys;
        self.parameters = [NSMutableDictionary dictionary];
        self.handledObservers = [NSMutableSet set];
    }
    return self;
}

- (void) addHandledObserver: (UINotificationObserver *)observer {
    [_handledObservers addObject:observer];
}

- (BOOL) isHandled: (UINotificationObserver *)observer {
    return [_handledObservers containsObject:observer];
}

- (void) reset {
    _handledObservers = [NSMutableSet set];
}

@end
