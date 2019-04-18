//
//  UIImage+Util.h
//  RCTBarcode
//
//  Created by xukj on 2019/4/17.
//  Copyright © 2019 react-native-component. All rights reserved.
//

#import <UIKit/UIKit.h>

NS_ASSUME_NONNULL_BEGIN

@interface UIImage (Util)

// 为了提高识别率，需要压缩图片
- (UIImage *)compress;

@end

NS_ASSUME_NONNULL_END
