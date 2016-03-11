//
//  DataOutputStream.m
//  FunChat1
//
//  Created by 胡占静 on 14-12-4.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import "DataOutputStream.h"

@implementation DataOutputStream

- (id)init{
    self = [super init];
    if(self != nil){
        data = [[NSMutableData alloc] init];
        length = 0;
    }
    return self;
}

- (void)writeInt:(int32_t)v {
    int8_t ch[4];
    for(int32_t i = 0;i<4;i++){
        ch[i] = ((v >> ((3 - i)*8)) & 0x0ff);
    }
    [data appendBytes:ch length:4];
    length = length + 4;
}

- (void)writeShort:(int16_t)v {
    int8_t ch[2];
    ch[0] = (v & 0x0ff00)>>8;
    ch[1] = (v & 0x0ff);
    [data appendBytes:ch length:2];
    length = length + 2;
}

- (void)writeBytes:(NSData *)v {
    [data appendData:v];
    NSInteger len = [v length];
    length = length + len;
}


- (NSData *)toByteArray{
    return [[NSData alloc] initWithData:data];
}

@end
