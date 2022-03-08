package com.bubble.mapper;

import com.bubble.model.ScoreRecord;
import com.bubble.model.ScoreRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ScoreRecordMapper {
    long countByExample(ScoreRecordExample example);

    int deleteByExample(ScoreRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ScoreRecord record);

    int insertSelective(ScoreRecord record);

    List<ScoreRecord> selectByExample(ScoreRecordExample example);

    ScoreRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ScoreRecord record, @Param("example") ScoreRecordExample example);

    int updateByExample(@Param("record") ScoreRecord record, @Param("example") ScoreRecordExample example);

    int updateByPrimaryKeySelective(ScoreRecord record);

    int updateByPrimaryKey(ScoreRecord record);
}