include "base.thrift"
namespace java com.bubble.thrift.recommend_service
namespace py com.bubble.thrift.recommend_service

struct ItemBaseEntity {
    1: i32 id;
    2: string title;
    3: string genres;
}

struct SyncItemBaseRequest {
    1: list<ItemBaseEntity> ItemInfoEntityList;
    255: optional base.Base Base;
}

struct SyncItemBaseResponse {
    1: bool done;
    255: base.BaseResp BaseResp;
}

struct GetRecommendInfoRequest {
    1: list<string> AList;
    2: i32 startPosition;
    3: i32 endPosition;
    255: base.BaseResp BaseResp;
}

struct GetRecommendInfoResponse {
    1: list<string> ABList;
    2: list<string> BBList;
    255: base.BaseResp BaseResp;
}


service RecommendService {
    GetRecommendInfoResponse GetRecommendInfo(1:GetRecommendInfoRequest getRecommendInfoRequest);
    SyncItemBaseResponse SyncItemBase(1: SyncItemBaseRequest syncItemBaseRequest);
}
