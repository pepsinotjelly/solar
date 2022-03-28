package com.bubble.service;

import com.bubble.model.UserBase;
import com.bubble.vo.BaseUser;
import com.bubble.vo.UserEntity;

import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/25 7:58 PM
 * @Desc:
 */
public interface UserService {
    Integer register(UserBase userBase);
    Integer login(UserBase userBase);
    String getMyRecommend(int user_id) throws Exception;
    Integer rateMovie(int user_id,String rate,int movie_id,String comment)throws Exception;
    BaseUser findUserById(String id) throws Exception;
    UserEntity findUserEntityById(String id) throws Exception;
}
