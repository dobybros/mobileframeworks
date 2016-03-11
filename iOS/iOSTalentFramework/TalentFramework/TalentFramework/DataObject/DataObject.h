//
//  DataObject.h
//  FunChat1
//
//  Created by 胡占静 on 14-12-4.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import <Foundation/Foundation.h>

#define DATAOBJECT_ID @"id"

@interface DataObject : NSObject

@property (nonatomic, strong)NSString *objectId;

- (DataObject *)initWithId:(NSString *)objectId;

@end
