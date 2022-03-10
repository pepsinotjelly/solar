package com.bubble.service.impl;

import com.bubble.service.UserBaseService;
import com.bubble.mapper.UserBaseBMapper;
import com.bubble.model.UserBaseB;
import com.bubble.model.UserBaseBExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserBaseServiceImpl implements UserBaseService {
    @Resource
    private UserBaseBMapper userBaseBMapper;
    @Override
    public List<UserBaseB> getList() {
        UserBaseBExample userBaseBExample = new UserBaseBExample();
        userBaseBExample.createCriteria().andIdEqualTo(1);
        return userBaseBMapper.selectByExample(userBaseBExample);
    }
}
