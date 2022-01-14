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

struct GetUserInfoRequest {
    1: i32 UserId = 0;
    255: optional base.Base Base;
}

struct GetUserInfoResponse {
    1: UserInfo userInfo;
    255: base.BaseResp baseResp;
}

struct LoginRequest {
    1: i32 userId = 0;
    2: string password = "";
    255: optional base.Base Base;
}

struct LoginResponse {
    1: i32 userId = 0;
    2: json Cookie = {};
    255: base.BaseResp BaseResp;
}

service RecommendService {
    GetUserInfoResponse GetUserInfo(1: GetUserInfoRequest getUserInfoRequest);
    LoginResponse Login(1: LoginRequest loginRequest);
}