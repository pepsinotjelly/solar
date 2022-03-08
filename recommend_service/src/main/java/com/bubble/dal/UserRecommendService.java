package com.bubble.dal;

import com.bubble.model.UserRecommend;

import java.util.List;

public interface UserRecommendService {
    List<UserRecommend> getUserRecommendList(int userId);
    int updateUserRecommend(int userId, List<UserRecommend> userRecommendList);
}
