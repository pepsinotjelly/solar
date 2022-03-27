package com.bubble.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bubble.service.TokenService;
import com.bubble.vo.UserEntity;
import org.springframework.stereotype.Service;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/27 9:05 下午
 * @Desc :
 */
@Service
public class TokenServiceImpl implements TokenService {
    public String getToken(UserEntity user) {
        String token="";
        token= JWT.create().withAudience(user.getUserId())
                .sign(Algorithm.HMAC256(user.getUserPwd()));
        return token;
    }
}
