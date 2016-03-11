//
//  DataObject.m
//  FunChat1
//
//  Created by 胡占静 on 14-12-4.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import "DataObject.h"

@implementation DataObject

- (DataObject *)initWithId:(NSString *)objectId {
    self = [super init];
    if (self) {
        self.objectId = objectId;
    }
    return self;
}
@end
