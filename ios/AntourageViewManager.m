
#import "AntourageViewManager.h"
@import Antourage;

@implementation AntourageViewManager

RCT_EXPORT_MODULE()

- (UIView *)view
{
  return [[AntWidget shared] view];
}

+ (BOOL)requiresMainQueueSetup
{
  return YES;
}

- (NSDictionary *) constantsToExport {
  return @{
           @"ComponentHeight": @(AntWidget.width),
           @"ComponentWidth": @(AntWidget.width)
          };
}
  
  RCT_EXPORT_VIEW_PROPERTY(onViewerAppear, RCTBubblingEventBlock);
  RCT_EXPORT_VIEW_PROPERTY(onViewerDisappear, RCTBubblingEventBlock);
  RCT_EXPORT_VIEW_PROPERTY(widgetMargins, NSDictionary);
  RCT_EXPORT_VIEW_PROPERTY(widgetPosition, NSString);


@end


  
