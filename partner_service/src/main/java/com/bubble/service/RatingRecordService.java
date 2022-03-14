package com.bubble.service;

import com.bubble.thrift.recommend_service.GetRecommendInfoRequest;
import com.bubble.thrift.recommend_service.GetRecommendInfoResponse;
import com.bubble.thrift.recommend_service.GetRecommendItemIdRequest;
import com.bubble.thrift.recommend_service.GetRecommendItemIdResponse;
import org.apache.thrift.TException;

public interface RatingRecordService {
    GetRecommendInfoResponse GetRecommendInfo(GetRecommendInfoRequest request) throws TException;
    GetRecommendItemIdResponse GetRecommendItemId(GetRecommendItemIdRequest getRecommendItemIdRequest) throws TException;
}
