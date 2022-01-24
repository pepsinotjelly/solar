package com.bubble.dal;

import com.bubble.model.UserInfo;

import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/12 11:49 AM
 * @Desc:
 */
public interface UserInfoService {
    List<UserInfo> getList();
    Integer save(UserInfo userInfo);
    List<UserInfo> getUserInfoById(int userId);
}