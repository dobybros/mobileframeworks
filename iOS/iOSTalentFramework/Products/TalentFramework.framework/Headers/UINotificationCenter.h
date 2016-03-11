//
//  UINotificationCenter.h
//  TalentFramework
//
//  Created by 胡占静 on 16/2/4.
//  Copyright © 2016年 jianming. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "TreeNode.h"
#import "UINotification.h"

@interface UINotificationCenter : NSObject

@property (nonatomic, strong) TreeNode *tree;
@property (nonatomic, strong) UINotification *currentNotification;
@property (nonatomic, strong) NSMutableDictionary *pendingNotificationsWhileRegister;

+ (UINotificationCenter *) defaultUINotificationCenter;

- (void) registerNotificationForObserver: (UINotificationObserver *)observer;

- (void) unregisterNotificationForObserver: (UINotificationObserver *)observer;

- (void) sendNotification: (UINotification *)notification;

@end
