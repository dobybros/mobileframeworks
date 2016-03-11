//
//  TreeNode.m
//  TalentFramework
//
//  Created by 胡占静 on 16/2/3.
//  Copyright © 2016年 jianming. All rights reserved.
//

#import "TreeNode.h"

@implementation TreeNode

- (instancetype)init {
    self = [super init];
    if (self) {
        _childrenTreeNodeDic = [NSMutableDictionary dictionary];
        _parameterDic = [NSMutableDictionary dictionary];
    }
    return self;
}

- (void)deleteObjectForKey:(NSString *)key {
    if ([_parameterDic objectForKey:key]) {
        [_parameterDic removeObjectForKey:key];
    }
}

- (TreeNode *) getChildrenTreeNodeForKey: (NSString *)key creat: (BOOL)creat {
    TreeNode *childrenTreeNode = [_childrenTreeNodeDic objectForKey:key];
    if (creat && childrenTreeNode == nil) {
        @synchronized(self) {
            childrenTreeNode = [_childrenTreeNodeDic objectForKey:key];
            if (childrenTreeNode == nil) {
                childrenTreeNode = [[TreeNode alloc] init];
                [_childrenTreeNodeDic setValue:childrenTreeNode forKey:key];
            }
        }
    }
    return childrenTreeNode;
}

@end
