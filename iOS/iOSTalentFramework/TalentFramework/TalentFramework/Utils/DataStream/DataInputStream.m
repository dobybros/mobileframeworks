//
//  DataInputStream.m
//  FunChat1
//
//  Created by 胡占静 on 14-12-4.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import "DataInputStream.h"

@implementation DataInputStream

- (id)initWithData:(NSData *)aData {
    self = [self init];
    if(self != nil){
        _data = [[NSData alloc] initWithData:aData];
    }
    return self;
}

- (id)init{
    self = [super init];
    if(self != nil){
        _length = 0;
    }
    return self;
}

+ (id)dataInputStreamWithData:(NSData *)aData {
    DataInputStream *dataInputStream = [[self alloc] initWithData:aData];
    return dataInputStream;
}

- (int8_t)readChar:(NSInteger)charLenth {
    int8_t v;
    [_data getBytes:&v range:NSMakeRange(_length,charLenth)];
    _length++;
    return (v & 0x0ff);
}

- (int16_t)readShort {
    int32_t ch1 = [self read];
    int32_t ch2 = [self read];
    if ((ch1 | ch2) < 0){
        @throw [NSException exceptionWithName:@"Exception" reason:@"EOFException" userInfo:nil];
    }
    return (int16_t)((ch1 << 8) + (ch2 << 0));
    
}

- (int32_t)readInt {
    int32_t ch1 = [self read];
    int32_t ch2 = [self read];
    int32_t ch3 = [self read];
    int32_t ch4 = [self read];
    if ((ch1 | ch2 | ch3 | ch4) < 0){
        @throw [NSException exceptionWithName:@"Exception" reason:@"EOFException" userInfo:nil];
    }
    return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
}

- (NSString *)readUTF {
    short utfLength = [self readShort];
    NSData *d = [_data subdataWithRange:NSMakeRange(_length,utfLength)];
    NSString *str = [[NSString alloc] initWithData:d encoding:NSUTF8StringEncoding];
    _length = _length + utfLength;
    return str;
}

- (int32_t)read{
    int8_t v;
    [_data getBytes:&v range:NSMakeRange(_length,1)];
    _length++;
    return ((int32_t)v & 0x0ff);
}

@end
