package com.bubble.controller;

import com.alibaba.fastjson.JSON;
//import com.bubble.constant.annotations.UserLoginToken;
import com.alibaba.fastjson.JSONObject;
import com.bubble.mapper.UserBaseMapper;
import com.bubble.mapper.UserInfoMapper;
import com.bubble.model.UserBase;
import com.bubble.model.UserBaseExample;
import com.bubble.service.ItemService;
import com.bubble.service.UserService;
import com.bubble.vo.ResponseEntity;
import com.bubble.vo.user.UserEntity;
import com.bubble.vo.user.UserRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
//        userBaseMapper.insert(userBase);
//        //  保证数据一致性
//        UserBaseExample userBaseExample = new UserBaseExample();
//        userBaseExample.createCriteria().andNameEqualTo(userRegister.getUserName());
//        List<UserBase> userBaseList = userBaseMapper.selectByExample(userBaseExample);
//        if (userBaseList.isEmpty()) {
//            response.setStatus(500);
//            response.setMsg("System update user error!");
//            log.info("userRegister :: System update user error! username:: " + userRegister.getUserName());
//            return (JSON) JSON.toJSON(response);
//        }
//        //  更新用户信息
//        UserInfo userInfo = new UserInfo();
//        userInfo.setName(userRegister.getUserName());
//        userInfo.setEmail(userRegister.getUserEmail());
//        userInfo.setId(userBaseList.get(0).getId());
//        userInfoMapper.insert(userInfo);
        //  创建返回值
        response.setMsg("user register success");
        response.setStatus(200);
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

    //    //  上传地址
//    @Value("${file.upload.path}")
//    private String path;
//
//    //  图片url前缀
//    @Value("${com.test.base_picture_url}")
//    private String base_picture_url;
//
//    @PostMapping("/update/avatar")
//    public JSON restoreUserRegisterAvatar(@RequestParam(value = "avatar") MultipartFile[] file){
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
    @RequestMapping(value = "/update/avatar", method = RequestMethod.POST)
    public String getSubscription(MultipartFile file, HttpSession session) throws IOException {
        log.info("image ::");
        //获取文件的内容
        if(file != null){
            InputStream is = file.getInputStream();
            //获取原始文件名
            String originalFilename = file.getOriginalFilename();
            log.info(originalFilename);
        }
        log.info("file null :: 吗的");

       return "ok";
    }

    public static void writePicture(MultipartFile file, String fileName, String fileFolderName) {
        try {
            FileOutputStream picOutput = new FileOutputStream("/outfits/" + fileFolderName + "/" + fileName);            //设置文件路径
            picOutput.write(file.getBytes());   //获取字节流直接写入到磁盘内
            picOutput.close();
        } catch (Exception e) {
        }
    }
}
