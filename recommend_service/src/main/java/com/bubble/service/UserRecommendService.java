package com.bubble.service;

import com.bubble.model.ItemBase;
import com.bubble.model.UserRecommend;

import java.util.List;

public interface UserRecommendService {
    List<UserRecommend> getUserRecommendList(int userId);
    int updateUserRecommend(int userId, List<UserRecommend> userRecommendList);
    List<String> getSimilarityList(int userId) throws Exception;
    List<Integer> getRecommendList(List<String> cosineSimilarity,List<String> Index) throws Exception;
    List<String> getPlainSimilarityList(int userId) throws Exception;
    List<Integer> getPlainRecommendList(List<String> cosineSimilarity, List<String> Index) throws Exception;
}
