package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.bubble.constant.annotations.UserLoginToken;
import com.bubble.service.ItemService;
//import com.bubble.dal.UserInfoService;
import com.bubble.service.TokenService;
import com.bubble.service.UserService;
import com.bubble.vo.BaseResp;
import com.bubble.vo.user.UserLoginRequest;
import com.bubble.vo.user.UserEntity;
import com.bubble.vo.user.UserRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
    public JSON userRegister(@RequestBody UserRegister userRegister) {
        //  数据校验 - email是否唯一值？
        //  数据持久化
        //  获取id
        log.info(String.valueOf((JSON) JSON.toJSON(userRegister)));
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserPwd(userRegister.getUserPwd());
        userLoginRequest.setUserEmail("12345@qq.com");
        String token = tokenService.getToken("1", userRegister.getUserPwd());
        BaseResp baseResp = new BaseResp();
        baseResp.setBaseCode(0);
        baseResp.setBaseMsg("success");
        baseResp.setToken(token);
        return (JSON)JSON.toJSON(baseResp);
    }

    @PostMapping(value = "/login")
    public JSON userLogin(@RequestBody UserLoginRequest user) throws Exception {
        JSONObject jsonObject = new JSONObject();
        UserLoginRequest userForBase = userService.findUserByEmail(user.getUserEmail());
        log.info("userForBase.getUserPwd() :: "+userForBase.getUserPwd());
        if (userForBase == null) {
            jsonObject.put("baseMsg", "登录失败,用户不存在");
            jsonObject.put("baseCode",1);
        } else {
            if (!userForBase.getUserPwd().equals(user.getUserPwd())) {
                jsonObject.put("baseMsg", "登录失败,密码错误");
                jsonObject.put("baseCode",2);
            } else {
                String token = tokenService.getToken("1",userForBase.getUserPwd());
                BaseResp baseResp = new BaseResp();
                baseResp.setBaseCode(0);
                baseResp.setBaseMsg("success");
                baseResp.setToken(token);
                 return (JSON)JSON.toJSON(baseResp);
            }
        }
        return jsonObject;
    }

    @UserLoginToken
    @GetMapping("/info")
    public JSON getUserInfo(@RequestHeader("Authorization") String token)throws Exception{
        //  通过token获取userId
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new RuntimeException("401");
        }
        //  获取用户信息
        UserEntity userEntity = userService.findUserEntityById(userId);
        //  构造返回值
        return (JSON)JSON.toJSON(userEntity);
    }

//    //  上传地址
//    @Value("${file.upload.path}")
//    private String path;
//
//    //  图片url前缀
//    @Value("${com.test.base_picture_url}")
//    private String base_picture_url;

//    @PostMapping("/update/avatar")
//    public JSON restoreUserRegisterAvatar(@RequestBody MultipartFile[] file){
//        log.info("get file :: "+ file.length);
//        //String base_picture_url = "http://localhost:8080/image/";
//
//        //获取文件在服务器的储存位置
//        File filePath = new File(path);
//        log.info("image file path :: "+path);
//        if(!filePath.exists() && !filePath.isDirectory()){
//            log.info("file path is not EXIST! CREATE path :: " + filePath);
//            filePath.mkdir();
//        }
//
//        //获取原始文件名称（包括格式）
//        String originalFileName = file[0].getOriginalFilename();
//        System.out.println("原始文件名称" + originalFileName);
//
//        //获取文件类型，以最后一个‘.’为标识
//        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
//        System.out.println("文件类型" + type);
//
//        //获取文件名称（不包含格式）
//        String name = originalFileName.substring(0,originalFileName.lastIndexOf("."));
//
//        //设置文件新名称：当前事件+文件名称（不包含格式）
//        Date d = new Date();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String date = sdf.format(d);
//        String fileName = date + name + "." +type;
//        System.out.println("新文件名称" +fileName);
//
//        //在指定路径下创建文件
//        File targetFile = new File(path,fileName);
//
//        Map<String,Object> result = new HashMap<String,Object>();//定义结果
//        //将文件保存到服务器指定位置
//        try{
//            file[0].transferTo(targetFile);
//            log.info("上传成功");
//
//        }catch (IOException e){
//            log.info("上传失败");
//            result.put("code",400);
//            e.printStackTrace();
//            return new JSONObject(result);
//        }
//
//        result.put("code",200);
//        result.put("picture",base_picture_url+ fileName);
//        System.out.println(result);
//        return new JSONObject(result);
//    }
}
