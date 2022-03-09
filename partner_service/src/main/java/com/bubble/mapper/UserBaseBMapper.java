package com.bubble.mapper;

import com.bubble.model.UserBaseB;
import com.bubble.model.UserBaseBExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserBaseBMapper {
    long countByExample(UserBaseBExample example);

    int deleteByExample(UserBaseBExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserBaseB record);

    int insertSelective(UserBaseB record);

    List<UserBaseB> selectByExample(UserBaseBExample example);

    UserBaseB selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UserBaseB record, @Param("example") UserBaseBExample example);

    int updateByExample(@Param("record") UserBaseB record, @Param("example") UserBaseBExample example);

    int updateByPrimaryKeySelective(UserBaseB record);

    int updateByPrimaryKey(UserBaseB record);
}