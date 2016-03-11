//
//  DataInputStream.h
//  FunChat1
//
//  Created by 胡占静 on 14-12-4.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import <Foundation/Foundation.h>

// 从输入流读取基本数据类型的方法，以便解组自定义值类型

@interface DataInputStream : NSObject
//{
//    NSData *data;
//    NSInteger length;
//}

@property (nonatomic, strong)NSData *data;
@property (nonatomic)NSInteger length;

- (id)initWithData:(NSData *)data;

+ (id)dataInputStreamWithData:(NSData *)aData;

- (int8_t)readChar:(NSInteger)charLenth;

- (int32_t)readInt;

- (int16_t)readShort;

- (NSString *)readUTF;

@end
