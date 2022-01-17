include "base.thrift"
namespace java com.bubble.thrift.recommend_service

struct UserInfo {
    1: i32 id = 0;
    2: string userName = "";
    3: optional i16 gender = 0;
    4: optional i16 age = 0;
    5: optional i16 degree = 0;
    6: optional i16 continent = 0;
    7: optional i32 consumptionCapacity = 0;
    8: optional i16 userStatus = 0;
    9: string createTime = "";
    10: string modifyTime = "";
}

struct Commodity {
}
struct BatchGetUserInfoRequest {
    1: i32 UserId = 0;
    255: optional base.Base Base;
}

struct BatchGetUserInfoResponse {
    1: UserInfo userInfo;
    255: base.BaseResp baseResp;
}

struct UserLoginRequest {
    1: i32 userId = 0;
    2: string password = "";
    255: optional base.Base Base;
}

struct UserLoginResponse {
    1: i32 userId = 0;
    2: json Cookie = {};
    255: base.BaseResp BaseResp;
}

service RecommendService {
    BatchGetUserInfoResponse BatchGetUserInfo(1: BatchGetUserInfoRequest batchGetUserInfoRequest);
    UserLoginResponse UserLogin(1: UserLoginRequest userLoginRequest);
}

struct BatchGetCommodityRequest {
}
struct BatchGetCommodityResponse {
}