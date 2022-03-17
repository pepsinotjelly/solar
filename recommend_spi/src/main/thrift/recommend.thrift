include "base.thrift"
namespace java com.bubble.thrift.recommend_service
namespace py com.bubble.thrift.recommend_service

struct ItemBaseEntity {
    1: i32 id;
    2: string title;
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
    4: string publicKeyN;
    5: string publicKeyRnd;
}

struct GetRecommendInfoResponse {
    1: list<string> ABList;
    2: list<string> BBList;
    3: i32 M;
    4: i32 N;
    255: base.BaseResp BaseResp;
}

struct GetItemIdRequest {
    1: list<string> indexList;
    2: list<string>  cosineSimilarityList;
    3: string publicKeyN;
    4: string publicKeyRnd;
}
struct GetItemIdResponse {
    1: list<string> itemIdList;
    2: list<string> ratingList;
    255: base.BaseResp BaseResp;
}

service RecommendService {
    GetRecommendInfoResponse GetRecommendInfo(1:GetRecommendInfoRequest getRecommendInfoRequest);
    SyncItemBaseResponse SyncItemBase(1: SyncItemBaseRequest syncItemBaseRequest);
    GetItemIdResponse GetItemId(1: GetItemIdRequest getItemIdRequest);
    GetRecommendInfoResponse GetPlainRecommendInfo(1:GetRecommendInfoRequest getRecommendInfoRequest);
    GetItemIdResponse GetPlainItemId(1: GetItemIdRequest getItemIdRequest);
}
