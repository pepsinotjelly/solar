package com.bubble.service;

import com.bubble.thrift.recommend_service.GetItemIdRequest;
import com.bubble.thrift.recommend_service.GetItemIdResponse;
import com.bubble.thrift.recommend_service.GetRecommendInfoRequest;
import com.bubble.thrift.recommend_service.GetRecommendInfoResponse;
import org.apache.thrift.TException;

public interface RatingRecordService {
    GetRecommendInfoResponse GetRecommendInfo(GetRecommendInfoRequest request) throws TException;
    GetItemIdResponse GetItemId(GetItemIdRequest getItemIdRequest) throws TException;
    GetRecommendInfoResponse GetPlainRecommendInfo(GetRecommendInfoRequest request) throws TException;
    GetItemIdResponse GetPlainRecommendItemId(GetItemIdRequest request) throws TException;
}
