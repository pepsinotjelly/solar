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
        System.out.println("hello list");
        System.out.println(userInfoMapper);
        UserInfoExample userInfoExample = new UserInfoExample();
        userInfoExample.createCriteria().andAgeIsNotNull();
        List<UserInfo> listUser =  userInfoMapper.selectByExample(userInfoExample);
        for(UserInfo item:listUser){
            System.out.println(item.getAge());
        }
        return listUser;
    }

    @Override
    public Integer save(UserInfo userInfo) {
        return userInfoMapper.insert(userInfo);
    }
}
