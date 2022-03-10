package com.bubble.service.impl;

import com.bubble.service.UserRecommendService;
import com.bubble.mapper.UserRecommendMapper;
import com.bubble.model.UserRecommend;
import com.bubble.model.UserRecommendExample;
import com.bubble.utils.IdWorker;

import javax.annotation.Resource;
import java.util.List;

public class UserRecommendServiceImpl implements UserRecommendService {
    @Resource
    UserRecommendMapper userRecommendMapper;


    @Override
    public List<UserRecommend> getUserRecommendList(int userId) {
        UserRecommendExample userRecommendExample = new UserRecommendExample();
        userRecommendExample.createCriteria().andIdEqualTo(userId);
        return userRecommendMapper.selectByExample(userRecommendExample);
    }

    @Override
    public int updateUserRecommend(int userId, List<UserRecommend> userRecommendList) {
        UserRecommendExample userRecommendExample = new UserRecommendExample();
        userRecommendExample.createCriteria().andUserIdEqualTo(userId);
        userRecommendMapper.deleteByExample(userRecommendExample);
        for(UserRecommend userRecommend : userRecommendList){
            IdWorker worker = IdWorker.getIdWorker();
            userRecommend.setId(1);
            userRecommendMapper.insert(userRecommend);
        }
        return 0;
    }
}
