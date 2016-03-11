//
//  UINotification.h
//  TalentFramework
//
//  Created by 胡占静 on 16/2/4.
//  Copyright © 2016年 jianming. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "UINotificationObserver.h"

@interface UINotification : NSObject

/*
 * 所有的filters
 */
@property (nonatomic, strong) NSArray *keys;

/*
 * 通知所带参数
 */
@property (nonatomic, strong) NSMutableDictionary *parameters;

/*
 * 放置已经处理过的UINotificationObserver
 */
@property (nonatomic, strong) NSMutableSet *handledObservers;


- (instancetype) initWithKeys: (NSArray *)keys;

- (void) addHandledObserver: (UINotificationObserver *)observer;

- (BOOL) isHandled: (UINotificationObserver *)observer;

- (void) reset;

@end
