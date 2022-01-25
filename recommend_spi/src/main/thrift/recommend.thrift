include "base.thrift"
namespace java com.bubble.thrift.recommend_service
namespace py com.bubble.thrift.recommend_service

struct UserBaseEntity {
    1: i32 id;
    2: string password;
    3: string userName;
    4: i32 phoneNumber;
    5: string email;
    6: string createTime;
    7: string modifyTime;
}

struct UserInfoEntity {
    1: i32 id = 0;
    2: optional i16 gender = 0;
    3: optional i16 age = 0;
    4: optional i16 degree = 0;
    5: optional i16 continent = 0;
    6: optional i32 consumptionCapacity = 0;
    7: optional i16 userStatus = 0;
    8: string createTime = "";
    9: string modifyTime = "";
}

struct CommodityInfoEntity {
    1: i32 id;
    2: string name;
    3: i32 price;
    4: i32 firstLevelLable;
    5: i32 secondaryLable;
    6: i32 tertiaryLabel;
    7: i32 isDel;
    8: string createTime;
    9: string modifyTime;
    10: string feature;
}

struct GetUserInfoRequest {
    1: list<i32> UserId = 0;
    255: optional base.Base Base;
}

struct GetUserInfoResponse {
    1: list<UserInfoEntity> userInfoEntity;
    255: base.BaseResp baseResp;
}

struct UserLoginRequest {
    1: i32 userId = 0;
    2: string password = "";
    255: optional base.Base Base;
}

struct UserLoginResponse {
    1: string Reason = "";
    255: base.BaseResp BaseResp;
}

struct GetCommodityInfoRequest {
    1: list<i32> commodityInfoId;
    255: optional base.Base Base;
}

struct GetCommodityInfoResponse {
    1: list<CommodityInfoEntity> commodityInfoEntityList;
    255: base.BaseResp BaseResp;
}

struct GetRecommendCommodityInfoRequest {
    1: list<i32> UserId;
    255: optional base.Base Base;
}

struct GetRecommendCommodityInfoResponse {
    1: list<CommodityInfoEntity> commodityInfoEntityList;
    255: base.BaseResp BaseResp;
}

service RecommendService {
    GetUserInfoResponse GetUserInfo(1:GetUserInfoRequest getUserInfoRequest);
    GetUserInfoResponse BatchGetUserInfo(1: GetUserInfoRequest getUserInfoRequest);
    UserLoginResponse UserLogin(1: UserLoginRequest userLoginRequest);
    GetCommodityInfoResponse GetCommodityInfo(1:GetCommodityInfoRequest getCommodityInfoRequest);
    GetCommodityInfoResponse BatchGetCommodityInfo(1:GetCommodityInfoRequest getCommodityInfoRequest);
    GetRecommendCommodityInfoResponse GetRecommendCommodityInfo(1:GetRecommendCommodityInfoRequest getRecommendCommodityInfoRequest);
    GetRecommendCommodityInfoResponse BatchGetRecommendCommodityInfo(1:GetRecommendCommodityInfoRequest getRecommendCommodityInfoRequest );
}
