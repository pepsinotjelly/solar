package com.bubble.utils;

import com.bubble.service.RecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/16 7:01 下午
 * @Desc :
 */
@Slf4j
@Component
public class ScheduledTask {
    @Autowired
    private RecommendService recommendService;
    @Scheduled(cron = "0 57 22 * * *") // 完成任务后延迟完成任务时间
    public void scheduledTask() throws Exception {
        log.info("TASK TIME :: " + LocalDateTime.now());
        for(int i = 1;i <= 600;i ++){
            recommendService.getSimilarityList(i);
            Thread.sleep(500);
        }
    }
}
