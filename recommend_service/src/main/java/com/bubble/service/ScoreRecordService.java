package com.bubble.service;

import java.util.List;

public interface ScoreRecordService {
    List<ScoreRecord> getList();
    Integer register(ScoreRecord scoreRecord);
    Integer login(ScoreRecord scoreRecord);
    Integer insert(ScoreRecord scoreRecord);
}
