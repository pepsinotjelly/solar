package com.bubble.biz.impl;

import com.bubble.biz.RatingRecordBiz;
import com.bubble.service.ScoreRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingRecordBizImpl implements RatingRecordBiz {
    @Autowired
    private ScoreRecordService scoreRecordService;
}
