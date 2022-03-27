package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.bubble.service.ItemService;
//import com.bubble.dal.UserInfoService;
import com.bubble.vo.UserEntity;
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
@CrossOrigin("http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    @Autowired
    private ItemService itemBaseService;

    @GetMapping(value = "/syncItemBase")
    public String syncItemBase() throws Exception {
        Boolean result = itemBaseService.SyncItemBase();
        return result.toString();
    }
    @GetMapping("/test")
    public String test() {
        return "OK";
    }


    @PostMapping(value = "/register")
    public JSON userRegister(@RequestBody UserEntity userEntity){
        log.info(String.valueOf((JSON) JSON.toJSON(userEntity)));
        return (JSON) JSON.toJSON(userEntity);
    }

    @PostMapping (value = "/login")
    public JSON userLogin(@RequestBody UserEntity userEntity){
        log.info(String.valueOf((JSON) JSON.toJSON(userEntity)));
        return (JSON) JSON.toJSON(userEntity);
    }

}
