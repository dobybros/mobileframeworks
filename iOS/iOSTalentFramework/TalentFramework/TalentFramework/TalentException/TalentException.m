//
//  TalentException.m
//  FunChat1
//
//  Created by 胡占静 on 14-12-11.
//  Copyright (c) 2014年 huzhanjing. All rights reserved.
//

#import "TalentException.h"

@implementation TalentException

-(TalentException *)initWithName:(NSString *)aName reason:(NSString *)aReason i18nKey:(NSString *)i18nKey userInfo:(NSDictionary *)aUserInfo{
    self = [super initWithName:aName reason:aReason userInfo:aUserInfo];
    if (self) {
        self.i18nKey = i18nKey;
    }
    return self;
}

-(TalentException *)initWithName:(NSString *)aName reason:(NSString *)aReason serverCode:(NSInteger) serverCode userInfo:(NSDictionary *)aUserInfo {
    self = [super initWithName:aName reason:aReason userInfo:aUserInfo];
    if (self) {
        self.serverCode = serverCode;
    }
    return self;
}

-(NSString *) description
{
    return [NSString stringWithFormat:@"TalentException desp %@, serverCode %ld, i18n %@", super.description, self.serverCode, self.i18nKey];
}

@end
