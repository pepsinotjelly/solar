package com.bubble.controller;

//import com.bubble.dal.UserInfoService;

import com.bubble.model.ItemBase;
import com.bubble.service.ItemBaseService;
import com.bubble.service.UserBaseService;
import com.bubble.model.UserBase;
import com.bubble.service.UserRecommendService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/12 3:43 PM
 * @Desc:
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserBaseService userBaseService;
    @Autowired
    private ItemBaseService itemBaseService;
    @Autowired
    private UserRecommendService userRecommendService;

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    @GetMapping(value = "/list")
    public List<UserBase> getUserList() throws Exception {
        return userBaseService.getList();
    }

    @GetMapping(value = "/syncItemBase")
    public String syncItemBase() throws Exception {
        Boolean result = itemBaseService.SyncItemBase();
        return result.toString();
    }

    @GetMapping(value = "/getRecommendList")
    public List<String> getRecommendList() throws Exception {
        List<Integer> lists = new ArrayList<>();
        lists.add(1);
        return userRecommendService.getSimilarityList(lists);
    }

}
