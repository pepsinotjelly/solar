package com.bubble.biz;

import com.bubble.thrift.recommend_service.GetRecommendInfoRequest;
import org.apache.thrift.TException;

public interface ItemBaseBiz {
    boolean SyncItemBase() throws TException;
    String GetRecommendInfo(GetRecommendInfoRequest request) throws TException;
}
