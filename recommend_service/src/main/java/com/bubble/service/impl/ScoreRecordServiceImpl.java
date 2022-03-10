package com.bubble.service.impl;

import com.bubble.service.ScoreRecordService;
import com.bubble.mapper.ScoreRecordMapper;
import com.bubble.model.ScoreRecord;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ScoreRecordServiceImpl implements ScoreRecordService {
    @Resource
    ScoreRecordMapper scoreRecordMapper;
    @Override
    public List<ScoreRecord> getList() {
        return null;
    }

    @Override
    public Integer register(ScoreRecord scoreRecord) {
        return null;
    }

    @Override
    public Integer login(ScoreRecord scoreRecord) {
        return null;
    }

    @Override
    public Integer insert(ScoreRecord scoreRecord) {
        return scoreRecordMapper.insert(scoreRecord);
    }
}
