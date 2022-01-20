package com.bubble.biz;

import com.bubble.dal.UserInfoService;
import com.bubble.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/17 12:41 PM
 * @Desc:
 */
public class UserBiz {
    @Autowired
    private UserInfoService userInfoService;

    public List<UserInfo> GetUserInfo(int userId){
        return userInfoService.getUserInfoById(userId);
    }

    public List<UserInfo> BatchGetUserInfo(List<Integer> userIdList){
        List<UserInfo> userInfoList = new ArrayList<>();
        for(Integer userId : userIdList){
            for(UserInfo userInfo : userInfoService.getUserInfoById(userId)) {
                userInfoList.add(userInfo);
            }
        }
        return userInfoList;
    }
}
