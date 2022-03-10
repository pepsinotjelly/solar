package com.bubble.biz.impl;

import com.bubble.biz.UserBaseBiz;
import com.bubble.service.UserBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBaseBizImpl implements UserBaseBiz {
    @Autowired
    private UserBaseService userBaseService;

}
