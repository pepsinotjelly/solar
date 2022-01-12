package com.bubble.controller;

import com.bubble.model.UserInfo;
import com.bubble.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/7 2:28 PM
 * @Desc:
 */
@RestController
@RequestMapping("/user")
public class UserController {
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
