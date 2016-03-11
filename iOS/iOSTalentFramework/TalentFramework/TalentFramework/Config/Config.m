//
//  Config.m
//  FunChat1
//
//  Created by 胡占静 on 14-12-20.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import "Config.h"

@implementation Config

static Config *config;
+ (Config *)singleConfig{
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        NSString *path = [[NSBundle mainBundle] pathForResource:@"config" ofType:@"plist"];
        config = [[self alloc] initWithContentsOfFile:path];
    });
    return config;
}

@end
