package com.bubble.thrift;

import com.bubble.biz.UserBiz;
import com.bubble.thrift.recommend_service.*;
import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.bubble.model.UserInfo;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/14 6:30 PM
 * @Desc:
 */
public class RecommendServiceImpl implements RecommendService.Iface {
    UserBiz userBiz = new UserBiz();

    @Override
    public GetUserInfoResponse GetUserInfo(GetUserInfoRequest getUserInfoRequest) throws TException {
        return userBiz.GetUserInfo(getUserInfoRequest);
    }

    @Override
    public GetUserInfoResponse BatchGetUserInfo(GetUserInfoRequest getUserInfoRequest) throws TException {
        return userBiz.BatchGetUserInfo(getUserInfoRequest);
    }

    @Override
    public UserLoginResponse UserLogin(UserLoginRequest userLoginRequest) throws TException {
        return null;
    }

    @Override
    public GetCommodityInfoResponse GetCommodityInfo(GetCommodityInfoRequest getCommodityInfoRequest) throws TException {
        return null;
    }

    @Override
    public GetCommodityInfoResponse BatchGetCommodityInfo(GetCommodityInfoRequest getCommodityInfoRequest) throws TException {
        return null;
    }

    @Override
    public GetRecommendCommodityInfoResponse GetRecommendCommodityInfo(GetRecommendCommodityInfoRequest getRecommendCommodityInfoRequest) throws TException {
        return null;
    }

    @Override
    public GetRecommendCommodityInfoResponse BatchGetRecommendCommodityInfo(GetRecommendCommodityInfoRequest getRecommendCommodityInfoRequest) throws TException {
        return null;
    }
}
