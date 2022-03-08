package com.bubble.mapper;

import com.bubble.model.UserRecommend;
import com.bubble.model.UserRecommendExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRecommendMapper {
    long countByExample(UserRecommendExample example);

    int deleteByExample(UserRecommendExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserRecommend record);

    int insertSelective(UserRecommend record);

    List<UserRecommend> selectByExample(UserRecommendExample example);

    UserRecommend selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserRecommend record, @Param("example") UserRecommendExample example);

    int updateByExample(@Param("record") UserRecommend record, @Param("example") UserRecommendExample example);

    int updateByPrimaryKeySelective(UserRecommend record);

    int updateByPrimaryKey(UserRecommend record);
}