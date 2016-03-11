//
//  UINotificationObserver.h
//  TalentFramework
//
//  Created by 胡占静 on 16/2/4.
//  Copyright © 2016年 jianming. All rights reserved.
//

#import <Foundation/Foundation.h>
@class UINotification;
@class UINotificationObserver;

typedef void(^ReceiveNotification)(UINotification *notification, UINotificationObserver *observer);

@interface UINotificationObserver : NSObject

@property (nonatomic, copy) NSString *observerId;
@property (nonatomic, strong) NSArray *filters;
@property (nonatomic, assign) ReceiveNotification receiveNotification;

- (instancetype) initWithObserverId: (NSString *)observerId filters: (NSArray *)filters;

@end
