package com.bubble.service;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/27 9:05 下午
 * @Desc :
 */
public interface TokenService {
    String getToken(String userId,String userPwd);
}
