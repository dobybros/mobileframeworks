//
//  ViewController.m
//  TalentFrameworkTest
//
//  Created by 胡占静 on 16/2/18.
//  Copyright © 2016年 huzhanjing. All rights reserved.
//

#import "ViewController.h"
//#import <TalentFramework/UINotificationCenter.h>
#import "UINotificationCenter.h"

@interface ViewController ()

@property (nonatomic, copy) NSString *goString;

@end

@implementation ViewController

static int i = 0;
static int j = 0;

- (void)viewDidLoad {
    [super viewDidLoad];
    _goString = @"go go go";
    for (i = 0; i < 2; i++) {
        UINotificationObserver *observer0 = [[UINotificationObserver alloc] initWithObserverId:nil filters:[NSArray arrayWithObjects:@"first1", nil]];
        typeof(self) __weak weakSelf = self;
        observer0.receiveNotification = ^(UINotification *notification, UINotificationObserver *observer) {
            NSLog(@"%@", weakSelf.goString);
            NSLog(@"observer----key:%@", notification.parameters);
            NSLog(@"%@", observer.observerId);
        };
        [[UINotificationCenter defaultUINotificationCenter] registerNotificationForObserver:observer0];
        
        
    }
    
    for (j = 0; j < 2; j++) {
        UINotificationObserver *observer1 = [[UINotificationObserver alloc] initWithObserverId:[NSString stringWithFormat:@"id1%d", j] filters:[NSArray arrayWithObjects:@"first2", nil]];
        observer1.receiveNotification = ^(UINotification *notification, UINotificationObserver *observer) {
            NSLog(@"observer----key:%@", notification.parameters);
            NSLog(@"%@", observer.observerId);
        };
        [[UINotificationCenter defaultUINotificationCenter] registerNotificationForObserver:observer1];
    }
    
    // Do any additional setup after loading the view, typically from a nib.
}

- (void) sendNotification1 {
    NSLog(@"sendNotification:%@", [NSThread currentThread]);
    UINotification *notification1 = [[UINotification alloc] initWithKeys:[NSArray arrayWithObjects:@"first1", nil]];
    [notification1.parameters setObject:@"v01" forKey:@"k01"];
    [[UINotificationCenter defaultUINotificationCenter] sendNotification:notification1];
    NSLog(@"sendNotification:%@", [NSThread currentThread]);
}

- (void) sendNotification2 {
    NSLog(@"sendNotification:%@", [NSThread currentThread]);
    UINotification *notification1 = [[UINotification alloc] initWithKeys:[NSArray arrayWithObjects:@"first2", nil]];
    [notification1.parameters setObject:@"v12" forKey:@"k12"];
    [[UINotificationCenter defaultUINotificationCenter] sendNotification:notification1];
    NSLog(@"sendNotification:%@", [NSThread currentThread]);
}

- (void) addNotificationObserVer0 {
    NSLog(@"addNotificationObserVer:%@", [NSThread currentThread]);
    UINotificationObserver *observer = [[UINotificationObserver alloc] initWithObserverId:[NSString stringWithFormat:@"id0%d", i] filters:[[NSArray alloc] initWithObjects:@"first1", nil]];
    i++;
    observer.receiveNotification = ^(UINotification *notification, UINotificationObserver *observer) {
        NSLog(@"observer----key:%@", notification.parameters);
        NSLog(@"%@", observer.observerId);
    };
    [[UINotificationCenter defaultUINotificationCenter] registerNotificationForObserver:observer];
    NSLog(@"addNotificationObserVer:%@", [NSThread currentThread]);
}

- (void) addNotificationObserVer1 {
    NSLog(@"addNotificationObserVer:%@", [NSThread currentThread]);
    UINotificationObserver *observer = [[UINotificationObserver alloc] initWithObserverId:[NSString stringWithFormat:@"id1%d", j] filters:[[NSArray alloc] initWithObjects:@"first2", nil]];
    j++;
    observer.receiveNotification = ^(UINotification *notification, UINotificationObserver *observer) {
        NSLog(@"observer----key:%@", notification.parameters);
        NSLog(@"%@", observer.observerId);
    };
    [[UINotificationCenter defaultUINotificationCenter] registerNotificationForObserver:observer];
    NSLog(@"addNotificationObserVer:%@", [NSThread currentThread]);
}

- (IBAction)clickButton1:(id)sender {
//    [_thread1 start];
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        [self sendNotification1];
    });
}

- (IBAction)clickButton2:(id)sender {
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        [self addNotificationObserVer0];
    });
}

- (IBAction)clickButton3:(id)sender {
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        [self addNotificationObserVer1];
    });
}

- (IBAction)clickButton4:(id)sender {
    dispatch_async(dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0), ^{
        [self sendNotification2];
    });
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

@end
