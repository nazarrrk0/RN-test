#import "RNAntourage.h"
@import Antourage;

@implementation RNAntourage

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(authWithApiKey:(nonnull NSString *)apiKey
                  refUserId:(nullable NSString *)refUserId
                  nickname:(nullable NSString *)nickname)
{
  [AntWidget authWithApiKey:apiKey refUserId:refUserId nickname:nickname];
}

RCT_EXPORT_METHOD(showFeed)
{
  dispatch_async(dispatch_get_main_queue(), ^(void){
    [[AntWidget shared] showFeed];
  });
  
}

@end
