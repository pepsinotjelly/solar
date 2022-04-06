package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.bubble.mapper.UserBaseMapper;
import com.bubble.mapper.UserInfoMapper;
import com.bubble.model.UserBase;
import com.bubble.model.UserBaseExample;
import com.bubble.model.UserInfo;
import com.bubble.model.UserInfoExample;
import com.bubble.service.ItemService;
import com.bubble.service.UserService;
import com.bubble.vo.ResponseEntity;
import com.bubble.vo.user.UserEntity;
import com.bubble.vo.user.UserRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private ItemService itemBaseService;
    @Autowired
    private UserService userService;
    @Resource
    private UserBaseMapper userBaseMapper;
    @Resource
    private UserInfoMapper userInfoMapper;


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
    public JSON userRegister(@RequestBody UserRegister userRegister) throws Exception {
        //  数据校验 - name是否唯一值？
        ResponseEntity response = new ResponseEntity();
        if (!checkRegister(userRegister.getUserName())) {
            response.setStatus(401);
            response.setMsg("this name already exist! get a new one!");
            log.info("userRegister :: name already exist! username :: " + userRegister.getUserName());
            return (JSON) JSON.toJSON(response);
        }
        //  数据持久化
        UserBase userBase = new UserBase();
        userBase.setName(userRegister.getUserName());
        String password = bCryptPasswordEncoder.encode(userRegister.getUserPwd());
        userBase.setPassword(password);
        log.info("userRegister :: user password :: password");
        userBaseMapper.insert(userBase);
        //  保证数据一致性
        UserBaseExample userBaseExample = new UserBaseExample();
        userBaseExample.createCriteria().andNameEqualTo(userRegister.getUserName());
        List<UserBase> userBaseList = userBaseMapper.selectByExample(userBaseExample);
        if (userBaseList.isEmpty()) {
            response.setStatus(500);
            response.setMsg("System update user error!");
            log.info("userRegister :: System update user error! username:: " + userRegister.getUserName());
            return (JSON) JSON.toJSON(response);
        }
        //  新建用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userRegister.getUserName());
        userInfo.setEmail(userRegister.getUserEmail());
        userInfo.setId(userBaseList.get(0).getId());
        userInfo.setAvatarcolor("grey");
        userInfoMapper.insert(userInfo);
        //  创建返回值
        response.setMsg("user register success");
        response.setStatus(200);
        log.info("userRegister :: user register success :: " + String.valueOf((JSON) JSON.toJSON(response)));
        return (JSON) JSON.toJSON(response);
    }

    @RequestMapping(value = "/update/avatar", method = RequestMethod.POST)
    public JSON updateAvatar(@RequestBody String avatarColor) throws Exception {
        log.info("avatar color :: " + avatarColor);
        ResponseEntity response = new ResponseEntity();
        //  检查登陆态
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            log.info("getUserInfo :: user is not with AnonymousAuthenticationToken");
        } else {
            log.info("getUserInfo :: get AnonymousAuthenticationToken");
        }
        //  获取用户base信息
        if (authentication.getPrincipal() != null) {
            log.info("getUserInfo :: user not null");
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            log.info("getUserInfo :: " + username);
            //  获取用户信息
            UserInfoExample example = new UserInfoExample();
            example.createCriteria().andNameEqualTo(username);
            List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
            if(userInfoList.isEmpty()){
                response.setStatus(500);
                response.setMsg("server restore user error!");
                log.info("userRegister :: user register success :: " + String.valueOf((JSON) JSON.toJSON(response)));
                return (JSON) JSON.toJSON(response);
            }
            UserInfo userInfo = userInfoList.get(0);
            userInfo.setAvatarcolor(avatarColor);
            example.clear();
            example.createCriteria().andNameEqualTo(username);
            userInfoMapper.updateByExample(userInfo,example);
        }
        response.setStatus(200);
        response.setMsg("avatar update success");
        log.info("userRegister :: user register success :: " + String.valueOf((JSON) JSON.toJSON(response)));
        return (JSON) JSON.toJSON(response);
    }

    public boolean checkRegister(String username) throws Exception {
        if (username.trim() == null) {
            return false;
        }
        UserBaseExample example = new UserBaseExample();
        example.createCriteria().andNameEqualTo(username);
        List<UserBase> userBaseList = userBaseMapper.selectByExample(example);
        if (!userBaseList.isEmpty()) {
            return false;
        }
        return true;
    }


    @GetMapping("/info")
    public JSON getUserInfo(@RequestHeader("Authorization") String token) throws Exception {
        //  通过token获取userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            // 登入用户
            log.info("getUserInfo :: user is not with AnonymousAuthenticationToken");
        } else {
            log.info("getUserInfo :: get AnonymousAuthenticationToken");
        }
        if (authentication.getPrincipal() != null) {
            log.info("getUserInfo :: user not null");
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            log.info("getUserInfo :: " + username);
            //  获取用户信息
            UserEntity userEntity = userService.findUserEntityByUsername(username);
            return (JSON) JSON.toJSON(userEntity);
        }
        //  构造返回值
        log.info("getUserInfo :: cant get user,return null!");
        return (JSON) JSON.toJSON(new UserEntity());
    }
}
