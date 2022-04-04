package com.bubble.controller;

//import com.bubble.dal.UserInfoService;

import com.bubble.mapper.ItemBaseMapper;
import com.bubble.mapper.ItemTagMapper;
import com.bubble.mapper.TagMapper;
import com.bubble.mapper.UserBaseMapper;
import com.bubble.model.*;
import com.bubble.service.ItemService;
import com.bubble.service.RecommendService;
import com.bubble.service.UserService;
import com.bubble.utils.CryptoSystem;
import com.bubble.utils.DataProcessor;
import com.bubble.vo.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tomcat.util.digester.Digester;
import org.apache.velocity.runtime.directive.Evaluate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import paillierp.Paillier;
import paillierp.key.KeyGen;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.math.BigInteger;
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
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private ItemService itemBaseService;
    @Autowired
    private RecommendService recommendService;
    @Resource
    private ItemBaseMapper itemBaseMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private ItemTagMapper itemTagMapper;
    @Resource
    private UserBaseMapper userBaseMapper;

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    // 任何人都可以访问，在方法中判断用户是否合法
    @GetMapping("/everyone")
    public ResponseEntity testEveryone() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (! (authentication instanceof AnonymousAuthenticationToken)) {
            // 登入用户
            return new ResponseEntity(HttpStatus.OK.value(), "You are already login", authentication.getPrincipal());
        } else {
            return new ResponseEntity(HttpStatus.OK.value(), "You are anonymous", null);
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity testUser(@AuthenticationPrincipal UsernamePasswordAuthenticationToken token) {
        return new ResponseEntity(HttpStatus.OK.value(), "You are user", token);
    }


    @GetMapping(value = "/api/sync-item-base")
    public String syncItemBase() throws Exception {
        Boolean result = itemBaseService.SyncItemBase();
        return result.toString();
    }

    @GetMapping(value = "/api/get-recommend-list")
    public List<String> getRecommendList(@RequestParam(value = "user_id") int user_id) throws Exception {

        return recommendService.getSimilarityList(user_id);
    }

    @GetMapping(value = "/api/get-plain-recommend-list")
    public List<String> getPlainRecommendList(@RequestParam(value = "user_id") int user_id) throws Exception {
        return recommendService.getPlainSimilarityList(user_id);
    }

    @GetMapping(value = "/api/get-diff")
    public double[] getDiff() throws Exception {
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        return dataProcessor.getDiff(100);
    }


    @GetMapping(value = "/api/get-my-recommend")
    public String myRecommend(@RequestParam(value = "user_id") int user_id) throws Exception {
        log.info("userService.getMyRecommend(user_id)");
        return userService.getMyRecommend(user_id);
    }

    @PostMapping(value = "/api/rate-movie")
    public Integer rateMovie(
            @RequestBody int user_id
            , @RequestBody String rate
            , @RequestBody int movie_id
            , @RequestBody String comment) throws Exception {
        return userService.rateMovie(user_id, rate, movie_id, comment);
    }

    @GetMapping(value = "/security/password")
    public String EncodePassword() throws Exception {
        for(int i = 1;i <= 600;i ++){
            UserBase userBase = userBaseMapper.selectByPrimaryKey(i);
            String originPwd = "user_"+i;
            log.info("origin pwd :: "+originPwd);
            log.info("md5 :: "+ DigestUtils.md5(originPwd));
            String tempPwd = bCryptPasswordEncoder.encode(originPwd);
            log.info("tempPwd :: "+tempPwd);
            userBase.setPassword(tempPwd);
//            userBaseMapper.updateByPrimaryKey(userBase);
            log.info("updated user_"+userBase.getId());
        }
        return "done";

    }
    @GetMapping(value = "/security/update/userbase")
    public String UpdateUserBase()throws Exception{
        for(int i = 1;i <= 600;i ++){
            UserBase userBase = userBaseMapper.selectByPrimaryKey(i);
            log.info("origin :: "+userBase);
            userBase.setName("user_"+i);
            userBaseMapper.updateByPrimaryKey(userBase);
            log.info("updated user_"+userBase.getId());
        }
        return "done";
    }


    @GetMapping(value = "/security/matches")
    public String MatchesPassword() throws Exception {
        for(int i = 1;i <= 600;i ++){
            UserBase userBase = userBaseMapper.selectByPrimaryKey(i);
            String tempPwd = userBase.getPassword();
            String testPwd = "user_"+i;
            boolean mathes = bCryptPasswordEncoder.matches(testPwd,tempPwd);
            log.info("matches :: "+String.valueOf(mathes) + "user_"+userBase.getId());
        }
        return "123";
    }
}
