//
//  ViewController.m
//  HelloObjc
//
//  Created by Jim Sproch on 7/21/20.
//  Copyright © 2020 Jim Sproch. All rights reserved.
//

#import "ViewController.h"
#import "SkCanvas.h"
#import "SkGraphics.h"
#import "SkSurface.h"
#import "SkString.h"
#import "SkRRect.h"

@interface ViewController ()

@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    self.view.backgroundColor = [UIColor lightGrayColor];
    
    CGFloat width = self.view.bounds.size.width;
    CGFloat height = self.view.bounds.size.height;
    
    CGFloat offsetX = width/2-206/2;
    CGFloat offsetY = height/2-240/2;
    
    SkBitmap bitmap;
    
    bitmap.setInfo(SkImageInfo::Make(width, height, kRGBA_8888_SkColorType, kOpaque_SkAlphaType));
    bitmap.allocPixels();
    
    SkCanvas canvas(bitmap);
    canvas.clear(SK_ColorWHITE);
    
    SkPaint paint;
    paint.setStyle(SkPaint::kFill_Style);
    paint.setAntiAlias(true);
    paint.setStrokeWidth(4);
    paint.setColor(0xff4285F4);
    
    SkRect rect = SkRect::MakeXYWH(offsetX+10, offsetY+10, 100, 160);
    canvas.drawRect(rect, paint);

    SkRRect oval;
    oval.setOval(rect);
    oval.offset(40, 80);
    paint.setColor(0xffDB4437);
    canvas.drawRRect(oval, paint);

    paint.setColor(0xff0F9D58);
    canvas.drawCircle(offsetX+180, offsetY+50, 25, paint);

    rect.offset(80, 50);
    paint.setColor(0xffF4B400);
    paint.setStyle(SkPaint::kStroke_Style);
    canvas.drawRoundRect(rect, 10, 10, paint);
    
    
    void* data = bitmap.getPixels();
    
    NSUInteger dataLength = width * height * 4;
    CGDataProviderRef ref = CGDataProviderCreateWithData(NULL, data, dataLength, NULL);
    CGColorSpaceRef colorspace = CGColorSpaceCreateDeviceRGB();
    CGImageRef iref = CGImageCreate(width, height, 8, 32, width * 4, colorspace, kCGBitmapByteOrder32Big | kCGImageAlphaPremultipliedLast, ref, NULL, true, kCGRenderingIntentDefault);
    UIGraphicsBeginImageContext(CGSizeMake(width, height));
    CGContextRef cgcontext = UIGraphicsGetCurrentContext();
    CGContextTranslateCTM(cgcontext, 0, height);
    CGContextScaleCTM(cgcontext, 1.0, -1.0);
    CGContextSetBlendMode(cgcontext, kCGBlendModeCopy);
    CGContextDrawImage(cgcontext, CGRectMake(0.0, 0.0, width, height), iref);
    
    UIImage *image = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    CFRelease(ref);
    CFRelease(colorspace);
    CGImageRelease(iref);
    bitmap.reset();
    
    UIImageView *imageView = [[UIImageView alloc] initWithImage: image];
    imageView.frame = CGRectMake(0, 0, width, height);
    
    [self.view addSubview:imageView];
}


@end
