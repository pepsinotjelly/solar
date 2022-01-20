package com.bubble.dal.impl;

import com.bubble.dal.UserInfoService;
import com.bubble.mapper.UserInfoMapper;
import com.bubble.model.UserInfo;
import com.bubble.model.UserInfoExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/12 11:50 AM
 * @Desc:
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
        return (List<UserInfo>) userInfoMapper.selectByExample(userInfoExample);
    }
}
