//
//  NotificationCenterEx.m
//  FunChat1
//
//  Created by 胡占静 on 14-12-19.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import "NotificationCenterEx.h"
#import "TalentException.h"
#import "TalentConstants.h"

#define TAG @"NotificationCenterEx"

@implementation NotificationCenterEx

static NotificationCenterEx *notificationCenterEx;
+ (NotificationCenterEx *)singleNotificationCenterEx{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        notificationCenterEx = [[self alloc] init];
        notificationCenterEx.observers = [NSMutableDictionary dictionary];
    });
    return notificationCenterEx;
}

- (void)registerObserver:(id<NotificationObserver>)observer forFilter: (NSString *)filter{
    NotificationEx *notificatin = _currentNotification;
    @synchronized(self) {
        if (![_observers objectForKey:filter]) {
            NSMutableArray *array = [NSMutableArray array];
            [array addObject:observer];
            [_observers setValue:array forKey:filter];
        } else {
            [[_observers objectForKey:filter] addObject:observer];
        }
        
        if (notificatin != nil) {
            [self sendNotification:notificatin];
        }
    }
}
- (void)unregisterObserver: (id<NotificationObserver>)observer forFilter: (NSString *)filter{
    @synchronized(self) {
        if ([_observers objectForKey:filter]) {
            NSMutableArray *array = [_observers objectForKey:filter];
            if ([array containsObject:observer]) {
                [array removeObject:observer];
            }
        }
    }
}

- (void) handleCallback: (id<NotificationObserver>) observer notification: (NotificationEx *) notification {
    @try {
        if (![notification isHandledForObserver:observer]) {
            [observer notificationReceived:notification];
            [notification addHandledObserver:observer];
        }
    }
    @catch (NSException *exception) {
        @throw [[TalentException alloc] initWithName:TAG reason:@"notificationReceived failed" i18nKey:MESSAGE_ERROR_NOTIFICATION_CALLBACK userInfo:nil];
    }
    @finally {
    }
   
}

- (void) sendNotification: (NotificationEx *) notification{
    @synchronized(self) {
        _currentNotification = notification;
        @try {
            //精准匹配
            NSMutableArray *observers = [_observers objectForKey:notification.name];
            for (id<NotificationObserver> observer in observers) {
                [self handleCallback:observer notification:notification];
            }
            
            //TODO 需要优化每次发送的时候都需要遍历observers的dictionary
            //假的通配符匹配
            for (NSString *name in [_observers allKeys]) {
                if ([name hasPrefix:@"*"]) {
                    NSString *str = [name substringWithRange:NSMakeRange(1, name.length - 1)];
                    if ([notification.name hasSuffix:str]) {
                        NSMutableArray *observers = [_observers objectForKey:name];
                        for (id<NotificationObserver> observer in observers) {
                            [self handleCallback:observer notification:notification];
                        }
                    }
                } else if ([name hasSuffix:@"*"]) {
                    NSString *str = [name substringWithRange:NSMakeRange(0, name.length - 1)];
                    if ([notification.name hasPrefix:str]) {
                        NSMutableArray *observers = [_observers objectForKey:name];
                        for (id<NotificationObserver> observer in observers) {
                            [self handleCallback:observer notification:notification];
                        }
                    }
                }
            }
        }
        @catch (NSException *exception) {
            
        }
        @finally {
            _currentNotification = nil;
        }
    }
}

@end
