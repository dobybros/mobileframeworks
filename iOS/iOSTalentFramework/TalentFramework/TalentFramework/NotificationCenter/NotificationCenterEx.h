//
//  NotificationCenterEx.h
//  FunChat1
//
//  Created by 胡占静 on 14-12-19.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "NotificationEx.h"

@protocol NotificationObserver <NSObject>

- (void)notificationReceived:(NotificationEx *)notification;

@end

@interface NotificationCenterEx : NSObject

@property (nonatomic, strong)NSMutableDictionary *observers;

@property (nonatomic, strong)NotificationEx *currentNotification;

+ (NotificationCenterEx *)singleNotificationCenterEx;

- (void)registerObserver:(id<NotificationObserver>)observer forFilter: (NSString *)filter;

- (void)unregisterObserver: (id<NotificationObserver>)observer forFilter: (NSString *)filter;

- (void) sendNotification: (NotificationEx *) notification;

@end