package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.bubble.vo.RatingRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/26 9:41 上午
 * @Desc :
 */
@Slf4j
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/rating")
public class RatingController {
    @PostMapping("/record")
    public JSON SubmitRatingRecord(@RequestBody RatingRecord ratingRecord){
        log.info(String.valueOf(ratingRecord));
        log.info(String.valueOf((JSON) JSON.toJSON(ratingRecord)));
        return (JSON) JSON.toJSON(ratingRecord);
    }
}
