package com.bubble.mapper;

import com.bubble.model.RatingRecord;
import com.bubble.model.RatingRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RatingRecordMapper {
    long countByExample(RatingRecordExample example);

    int deleteByExample(RatingRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RatingRecord record);

    int insertSelective(RatingRecord record);

    List<RatingRecord> selectByExample(RatingRecordExample example);

    RatingRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RatingRecord record, @Param("example") RatingRecordExample example);

    int updateByExample(@Param("record") RatingRecord record, @Param("example") RatingRecordExample example);

    int updateByPrimaryKeySelective(RatingRecord record);

    int updateByPrimaryKey(RatingRecord record);
}