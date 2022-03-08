package com.bubble.controller;

import com.bubble.dal.UserBaseService;
//import com.bubble.dal.UserInfoService;
import com.bubble.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/7 2:28 PM
 * @Desc: 用户填写、拉取数据
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
//    private UserInfoService userInfoService;
    private UserBaseService userBaseService;
    private IdWorker idWorker;
    //TODO
    //

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

//    @GetMapping(value = "/list")
//    public List<UserInfo> getUserList() throws Exception {
//        return userInfoService.getList();
//    }

//    @PostMapping(value = "/login")
//    public Integer userLogin() throws Exception {
//        // demo
//        // TODO signature 签名 + 校验签名
//        UserBase userBase = new UserBase();
//        // TODO  uuid生成唯一key，维护会话
//
//        return userBaseService.login(userBase);
//    }

    @PostMapping(value = "/register")
//    public Integer userRegister(@RequestParam String userName, @RequestParam String passWord, @RequestParam String phoneNumber) throws Exception {
//        UserBase userBase = new UserBase();
//        userBase.setUserName(userName);
//        userBase.setPassword(passWord);
//        userBase.setPhoneNumber(phoneNumber);
//        return userBaseService.register(userBase);
//    }
    @GetMapping(value = "/register")
    public String getRegister(){
        return "user/register";
    }

//    @GetMapping(value = "/recommend")
//    public List<ItemBase> userRecommend(){
//        List<ItemInfo> recommend_list = null;
//        ItemBase itemBase = new ItemBase();
//        // TODO get user_recommend
//
//        return recommend_list;
//    }
}
