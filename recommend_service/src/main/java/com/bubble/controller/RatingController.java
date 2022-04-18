package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.bubble.vo.RatingRecord;
import com.bubble.vo.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
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
        //  获取用户登陆信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (! (authentication instanceof AnonymousAuthenticationToken)) {
            // 登入用户
            log.info("SubmitRatingRecord :: user is not with AnonymousAuthenticationToken");
        } else {
            log.info("SubmitRatingRecord :: get AnonymousAuthenticationToken");
        }
        if(authentication.getPrincipal() != null){
            log.info("SubmitRatingRecord :: user not null");
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            log.info("SubmitRatingRecord :: "+username);

        }
        ResponseEntity response = new ResponseEntity();
        response.setStatus(200);
        response.setMsg("rating success");
        log.info(String.valueOf(ratingRecord));
        log.info(String.valueOf((JSON) JSON.toJSON(response)));
        return (JSON) JSON.toJSON(response);
    }
}
