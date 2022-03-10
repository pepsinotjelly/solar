package com.bubble.service;

import com.bubble.thrift.recommend_service.GetRecommendInfoRequest;
import com.bubble.thrift.recommend_service.GetRecommendInfoResponse;
import org.apache.thrift.TException;

public interface RatingRecordService {
    GetRecommendInfoResponse GetRecommendInfo(GetRecommendInfoRequest request) throws TException;
}
