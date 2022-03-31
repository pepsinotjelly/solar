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
import lombok.extern.slf4j.Slf4j;
import org.apache.velocity.runtime.directive.Evaluate;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
        for(int i = 2;i <= 600;i ++){
            UserBase userBase = userBaseMapper.selectByPrimaryKey(i);
            log.info("origin pwd :: "+userBase.getPassword());
            String tempPwd = bCryptPasswordEncoder.encode(userBase.getPassword());
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
