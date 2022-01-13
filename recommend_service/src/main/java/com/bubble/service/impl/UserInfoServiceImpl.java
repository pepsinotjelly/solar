package com.bubble.service.impl;

import com.bubble.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import com.bubble.model.UserInfo;
import com.bubble.model.*;
import com.bubble.mapper.UserInfoMapper;
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
}
