//
//  UIImage+Util.m
//  RCTBarcode
//
//  Created by xukj on 2019/4/17.
//  Copyright © 2019 react-native-component. All rights reserved.
//
//  modify by xukj - 调整压缩比例
//

#import "UIImage+Util.h"

#define COMPRESS_SCALE 0.5

@implementation UIImage (Util)

- (UIImage *)compress
{
    UIImage* bigImage = self;
    float actualHeight = bigImage.size.height;
    float actualWidth = bigImage.size.width;
    
    float newWidth = actualWidth * COMPRESS_SCALE;
    float newHeight = actualHeight * COMPRESS_SCALE;
    
    if (newWidth < 256 && newHeight < 256) {
        newWidth = actualWidth;
        newHeight = actualHeight;
    }
    
    CGRect rect =CGRectMake(0.0,0.0, newWidth, newHeight);
    UIGraphicsBeginImageContext(rect.size);
    [bigImage drawInRect:rect];// scales image to rect
    UIImage *theImage =UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    //RETURN
    return theImage;
}

@end
