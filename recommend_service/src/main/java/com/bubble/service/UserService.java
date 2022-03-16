package com.bubble.service;

import com.bubble.model.UserBase;

import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/25 7:58 PM
 * @Desc:
 */
public interface UserService {
//    List<UserBase> getList();
    Integer register(UserBase userBase);
    Integer login(UserBase userBase);
//    Integer insert(UserBase userBase);

}
