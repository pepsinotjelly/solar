package com.bubble.dal;

import com.bubble.model.ScoreRecord;
import com.bubble.model.UserBase;

import java.util.List;

public interface ScoreRecordService {
    List<ScoreRecord> getList();
    Integer register(ScoreRecord scoreRecord);
    Integer login(ScoreRecord scoreRecord);
    Integer insert(ScoreRecord scoreRecord);
}
