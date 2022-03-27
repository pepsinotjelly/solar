package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bubble.service.ItemService;
//import com.bubble.dal.UserInfoService;
import com.bubble.service.TokenService;
import com.bubble.service.UserService;
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
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

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

//    @PostMapping (value = "/login")
//    public JSON userLogin(@RequestBody UserEntity userEntity){
//        userEntity.setUserName("name_test");
//        userEntity.setUserAvatar("https://image.tmdb.org/t/p/w300_and_h450_bestv2/bNeE1kUMFYG1sG6blHMwkG9sXXM.jpg");
//        log.info(String.valueOf((JSON) JSON.toJSON(userEntity)));
//        return (JSON) JSON.toJSON(userEntity);
//    }
    @PostMapping(value = "/login")
    public JSON userLogin(@RequestBody UserEntity user) throws Exception {
        JSONObject jsonObject=new JSONObject();
        UserEntity userForBase=userService.findUserById(user.getUserId());
        if(userForBase==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            if (!userForBase.getUserPwd().equals(user.getUserPwd())){
                jsonObject.put("message","登录失败,密码错误");
                return jsonObject;
            }else {
                String token = tokenService.getToken(userForBase);
                jsonObject.put("token", token);
                jsonObject.put("user", userForBase);
                return jsonObject;
            }
        }
    }

}
