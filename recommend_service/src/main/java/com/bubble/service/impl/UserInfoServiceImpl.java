package com.bubble.service.impl;

import com.bubble.service.UserInfoService;
import com.bubble.mapper.UserInfoMapper;
import com.bubble.model.UserInfo;
import com.bubble.model.UserInfoExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/12 11:50 AM
 * @Desc: 数据层
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Override
    public List<UserInfo> getList() {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andAgeIsNotNull();
        return userInfoMapper.selectByExample(userInfoExample);
    }

    @Override
    public Integer save(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }

    @Override
    public List<UserInfo> getUserInfoById(int userId) {
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andIdEqualTo(userId);
        return userInfoMapper.selectByExample(userInfoExample);
    }

    @Override
    public List<UserInfo> batchGetUserInfoById(List<Integer> userIdList) {
        List<UserInfo> resultList = new ArrayList<>();
        for (int userId : userIdList) {
            List<UserInfo> userInfoList = getUserInfoById(userId);
            for (UserInfo userInfo : userInfoList) {
                resultList.add(userInfo);
            }
        }
        return resultList;
    }
}
