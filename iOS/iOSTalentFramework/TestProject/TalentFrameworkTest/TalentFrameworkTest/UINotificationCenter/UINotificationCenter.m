//
//  UINotificationCenter.m
//  TalentFramework
//
//  Created by 胡占静 on 16/2/4.
//  Copyright © 2016年 jianming. All rights reserved.
//

#import "UINotificationCenter.h"

@implementation UINotificationCenter

static UINotificationCenter *notificationCenter;
+ (UINotificationCenter *) defaultUINotificationCenter {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        notificationCenter = [[self alloc] init];
        notificationCenter.tree = [[TreeNode alloc] init];
        notificationCenter.pendingNotificationsWhileRegister = [NSMutableDictionary dictionary];
    });
    return notificationCenter;
}

- (void) registerNotificationForObserver: (UINotificationObserver *)observer {
    NSMutableSet *pendingNotifications = [NSMutableSet set];
    if (_currentNotification) {
        [pendingNotifications addObject:_currentNotification];
    }
    [_pendingNotificationsWhileRegister setObject:pendingNotifications forKey:observer.observerId];
    @synchronized(self) {
        @try {
            TreeNode *tree = self.tree;
            if (observer.filters && observer.filters.count > 0) {
                for (NSString *filter in observer.filters) {
                    TreeNode *childrenTreeNode = [tree getChildrenTreeNodeForKey:filter creat:YES];
                    tree = childrenTreeNode;
                }
            }
            [tree.parameterDic setValue:observer forKey:observer.observerId];
            if (pendingNotifications.count > 0) {
                for (UINotification *notification in pendingNotifications) {
                    NSArray *currentHandlingFilters = notification.keys;
                    BOOL hit = YES;
                    for (int i = 0; i < observer.filters.count; i ++) {
                        NSString *filter = observer.filters[i];
                        if (i >= currentHandlingFilters.count || ![filter isEqualToString:currentHandlingFilters[i]]) {
                            hit = NO;
                            break;
                        }
                    }
                    if (hit) {
                        [self handleNotification:notification forUINotificationObserver:observer];
                    }
                }
            }
        }
        @finally {
            if ([_pendingNotificationsWhileRegister objectForKey:observer.observerId]) {
                [_pendingNotificationsWhileRegister removeObjectForKey:observer.observerId];
            }
        }
    }
}

- (void) unregisterNotificationForObserver: (UINotificationObserver *)observer {
    @synchronized(self) {
        NSArray *filters = observer.filters;
        TreeNode *tree = self.tree;
        if (filters && filters.count > 0) {
            for (NSString *filter in observer.filters) {
                TreeNode *childrenTreeNode = [tree getChildrenTreeNodeForKey:filter creat:YES];
                tree = childrenTreeNode;
            }
        }
        [tree deleteObjectForKey:observer.observerId];
    }
}

- (void) sendNotification: (UINotification *)notification {
    @synchronized(self) {
        NSArray *pendingNotificationList = [_pendingNotificationsWhileRegister allValues];
        for (NSMutableSet *pendingNotifications in pendingNotificationList) {
            [pendingNotifications addObject:notification];
        }
        _currentNotification = notification;
        @try {
            [self handleCallback:notification forTreeNode:_tree];
            NSArray *filters = notification.keys;
            if (filters && filters.count > 0) {
                TreeNode *tree = _tree;
                for (NSString *filter in filters) {
                    TreeNode *childrenTreeNode = [tree getChildrenTreeNodeForKey:filter creat:YES];
                    if (childrenTreeNode) {
                        [self handleCallback:notification forTreeNode:childrenTreeNode];
                        tree = childrenTreeNode;
                    } else {
                        break;
                    }
                }
            }
        }
        @finally {
            _currentNotification = nil;
        }
    }
}

- (void) handleCallback: (UINotification *)notification forTreeNode: (TreeNode *)treeNode {
    NSArray *observers = [treeNode.parameterDic allValues];
    NSArray *copiedObservers = [observers copy];
    for (UINotificationObserver *observer in copiedObservers) {
        [self handleNotification:notification forUINotificationObserver:observer];
    }
}


- (void) handleNotification: (UINotification *)notification forUINotificationObserver: (UINotificationObserver *)observer {
    if (![notification isHandled:observer]) {
        @try {
            observer.receiveNotification(notification, observer);
//            if (observer.receiveNotification) {
//            }
            [notification addHandledObserver:observer];
        }
        @catch (NSException *exception) {
            NSLog(@"%@", exception.reason);
        }
    }
}

@end
