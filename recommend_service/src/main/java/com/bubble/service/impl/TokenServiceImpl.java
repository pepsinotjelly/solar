package com.bubble.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bubble.service.TokenService;
import org.springframework.stereotype.Service;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/27 9:05 下午
 * @Desc :
 */
@Service
public class TokenServiceImpl implements TokenService {
    public String getToken(String userId,String userPwd) {
        String token="";
        token= JWT.create().withAudience(userId)
                .sign(Algorithm.HMAC256(userPwd));
        return token;
    }
}
