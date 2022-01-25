package com.bubble.thrift;

import com.bubble.biz.UserInfoBiz;
import com.bubble.thrift.recommend_service.*;
import org.apache.thrift.TException;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/14 6:30 PM
 * @Desc:
 */
public class RecommendServiceImpl implements RecommendService.Iface {
    UserInfoBiz userBiz = new UserInfoBiz();

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
