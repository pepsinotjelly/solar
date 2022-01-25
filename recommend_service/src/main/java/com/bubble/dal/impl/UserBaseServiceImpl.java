package com.bubble.dal.impl;

import com.bubble.dal.UserBaseService;
import com.bubble.mapper.UserBaseMapper;
import com.bubble.model.UserInfo;
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
    public List<UserInfo> getList() {
        return null;
    }

    @Override
    public Integer save(UserInfo userInfo) {
        return null;
    }

}
