package com.bubble.service.impl;

import com.bubble.service.UserBaseService;
import com.bubble.mapper.UserBaseMapper;
import com.bubble.model.UserBase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/25 7:59 PM
 * @Desc:
 */
@Service
public class UserBaseServiceImpl implements UserBaseService {
    @Resource
    private UserBaseMapper userBaseMapper;

    @Override
    public List<UserBase> getList() {
        return null;
    }

    @Override
    public Integer register(UserBase userBase) {
        //TODO check_data
        userBaseMapper.insert(userBase);
        return 0;
    }

    @Override
    public Integer login(UserBase userBase) {
        return null;
    }

    @Override
    public Integer insert(UserBase userBase) {
        userBaseMapper.insert(userBase);
        return 0;
    }
}
