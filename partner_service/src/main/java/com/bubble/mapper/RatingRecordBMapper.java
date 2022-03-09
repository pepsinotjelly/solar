package com.bubble.mapper;

import com.bubble.model.RatingRecordB;
import com.bubble.model.RatingRecordBExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RatingRecordBMapper {
    long countByExample(RatingRecordBExample example);

    int deleteByExample(RatingRecordBExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RatingRecordB record);

    int insertSelective(RatingRecordB record);

    List<RatingRecordB> selectByExample(RatingRecordBExample example);

    RatingRecordB selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RatingRecordB record, @Param("example") RatingRecordBExample example);

    int updateByExample(@Param("record") RatingRecordB record, @Param("example") RatingRecordBExample example);

    int updateByPrimaryKeySelective(RatingRecordB record);

    int updateByPrimaryKey(RatingRecordB record);
}