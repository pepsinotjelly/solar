package com.bubble.dal;

import com.bubble.model.UserInfo;

import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/25 7:58 PM
 * @Desc:
 */
public interface UserBaseService {
    List<UserInfo> getList();
    Integer save(UserInfo userInfo);
}
