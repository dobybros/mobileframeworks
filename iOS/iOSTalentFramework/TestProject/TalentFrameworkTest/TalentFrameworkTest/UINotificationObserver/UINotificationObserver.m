//
//  UINotificationObserver.m
//  TalentFramework
//
//  Created by 胡占静 on 16/2/4.
//  Copyright © 2016年 jianming. All rights reserved.
//

#import "UINotificationObserver.h"

@implementation UINotificationObserver

- (instancetype) initWithObserverId: (NSString *)observerId filters: (NSArray *)filters {
    self = [super init];
    if (self) {
        if (!observerId) {
            CFUUIDRef puuid = CFUUIDCreate( nil );
            CFStringRef uuidString = CFUUIDCreateString( nil, puuid );
            NSString * result = (NSString *)CFBridgingRelease(CFStringCreateCopy( NULL, uuidString));
            CFRelease(puuid);
            CFRelease(uuidString);
            self.observerId = result;
        } else {
            self.observerId = observerId;
        }
        self.filters = filters;
    }
    return self;
}

@end
