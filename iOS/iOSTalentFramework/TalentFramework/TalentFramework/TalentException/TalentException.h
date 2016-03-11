//
//  TalentException.h
//  FunChat1
//
//  Created by 胡占静 on 14-12-11.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TalentException : NSException

@property (nonatomic, strong)NSString *i18nKey;
@property (nonatomic)NSInteger serverCode;

-(TalentException *)initWithName:(NSString *)aName reason:(NSString *)aReason i18nKey:(NSString *)i18nKey userInfo:(NSDictionary *)aUserInfo;

-(TalentException *)initWithName:(NSString *)aName reason:(NSString *)aReason serverCode:(NSInteger) serverCode userInfo:(NSDictionary *)aUserInfo;

@end
