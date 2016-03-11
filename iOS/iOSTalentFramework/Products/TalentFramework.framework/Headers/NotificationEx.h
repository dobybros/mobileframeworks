//
//  NotificationEx.h
//  FunChat1
//
//  Created by 胡占静 on 14-12-20.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NotificationEx : NSObject

@property (nonatomic, strong) NSDictionary *userInfo;
@property (nonatomic, strong) NSString *name;

@property (nonatomic, strong) NSMutableArray *handledObservers;

- (NotificationEx *)initWithName:(NSString *)name userInfo:(NSDictionary *)userInfo;

- (void)addHandledObserver: (id) observer;

- (BOOL)isHandledForObserver: (id) observer;


@end
