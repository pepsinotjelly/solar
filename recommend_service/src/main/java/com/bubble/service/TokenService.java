package com.bubble.service;

import com.bubble.vo.BaseUser;
import com.bubble.vo.UserEntity;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/27 9:05 下午
 * @Desc :
 */
public interface TokenService {
    String getToken(BaseUser user);
}
