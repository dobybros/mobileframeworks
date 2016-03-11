//
//  TreeNode.h
//  TalentFramework
//
//  Created by 胡占静 on 16/2/3.
//  Copyright © 2016年 jianming. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TreeNode : NSObject

/*
 * 放置下一级TreeNode
 */
@property (nonatomic, strong) NSMutableDictionary *childrenTreeNodeDic;

/*
 * 这个节点中的参数
 */
@property (nonatomic, strong) NSMutableDictionary *parameterDic;


/*
 * 删除该节点中的一个UINotificationObserver
 */
- (void) deleteObjectForKey: (NSString *)key;

/*
 * 获取下一级节点，如果没有就建一个
 */
- (TreeNode *) getChildrenTreeNodeForKey: (NSString *)key creat: (BOOL)creat;

@end
