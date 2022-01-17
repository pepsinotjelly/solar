package com.bubble.controller;

import com.bubble.model.UserInfo;
import com.bubble.dal.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/12 3:43 PM
 * @Desc:
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/test")
    public String test(){
        return "OK";
    }

    @GetMapping(value = "/list")
    public List<UserInfo> getUserList() throws Exception {
        return userInfoService.getList();
    }
}
