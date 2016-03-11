//
//  DataOutputStream.h
//  FunChat1
//
//  Created by 胡占静 on 14-12-4.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import <Foundation/Foundation.h>

// 数据输出流允许应用程序以适当方式将基本数据类型写入输出流中

@interface DataOutputStream : NSObject
{
    NSMutableData *data;
    NSInteger length;
}

- (void)writeInt:(int32_t)v;
- (void)writeShort:(int16_t)v;
- (void)writeBytes:(NSData *)v;
- (NSData *)toByteArray;

@end
