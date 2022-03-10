package com.bubble.service.impl;

import com.bubble.mapper.ItemBaseMapper;
import com.bubble.model.ItemBase;
import com.bubble.service.UserRecommendService;
import com.bubble.mapper.UserRecommendMapper;
import com.bubble.model.UserRecommend;
import com.bubble.model.UserRecommendExample;
import com.bubble.utils.IdWorker;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class UserRecommendServiceImpl implements UserRecommendService {
    @Resource
    UserRecommendMapper userRecommendMapper;

    @Resource
    private ItemBaseMapper itemBaseMapper;

    @Override
    public List<ItemBase> getRecommendList() throws Exception {
        List<ItemBase> recommendList = new ArrayList<>();
        // TODO
        //  计算AA
        //  获取AB、BB、
        //  计算余弦相似度
        //  排序结果
        //  获取Item列表
        //  构造返回值
        return recommendList;
    }

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
        for (UserRecommend userRecommend : userRecommendList) {
            IdWorker worker = IdWorker.getIdWorker();
            userRecommend.setId(1);
            userRecommendMapper.insert(userRecommend);
        }
        return 0;
    }
}
