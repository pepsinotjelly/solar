package com.bubble.controller;

//import com.bubble.dal.UserInfoService;

import com.bubble.mapper.*;
import com.bubble.model.*;
import com.bubble.service.ItemService;
import com.bubble.service.RecommendService;
import com.bubble.service.UserService;
import com.bubble.utils.DataProcessor;
import com.bubble.vo.ResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
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
    @Resource
    private ItemInfoMapper itemInfoMapper;

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    // 任何人都可以访问，在方法中判断用户是否合法
    @GetMapping("/everyone")
    public ResponseEntity testEveryone() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
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
        for (int i = 1; i <= 600; i++) {
            UserBase userBase = userBaseMapper.selectByPrimaryKey(i);
            String originPwd = "user_" + i;
            log.info("origin pwd :: " + originPwd);
            String MD5Pwd = DigestUtils.md5Hex(URLEncoder.encode(originPwd, StandardCharsets.UTF_8.toString()));
            log.info("md5 :: " + MD5Pwd);
            String tempPwd = bCryptPasswordEncoder.encode(MD5Pwd);
            log.info("tempPwd :: " + tempPwd);
            userBase.setPassword(tempPwd);
            userBaseMapper.updateByPrimaryKey(userBase);
            log.info("updated user_" + userBase.getId());
        }
        return "done";

    }

    @GetMapping(value = "/security/update/userbase")
    public String UpdateUserBase() throws Exception {
        for (int i = 1; i <= 600; i++) {
            UserBase userBase = userBaseMapper.selectByPrimaryKey(i);
            log.info("origin :: " + userBase);
            userBase.setName("user_" + i);
            userBaseMapper.updateByPrimaryKey(userBase);
            log.info("updated user_" + userBase.getId());
        }
        return "done";
    }


    @GetMapping(value = "/security/matches")
    public String MatchesPassword() throws Exception {
        for (int i = 1; i <= 600; i++) {
            UserBase userBase = userBaseMapper.selectByPrimaryKey(i);
            String tempPwd = userBase.getPassword();
            String testPwd = "user_" + i;
            boolean mathes = bCryptPasswordEncoder.matches(testPwd, tempPwd);
            log.info("matches :: " + String.valueOf(mathes) + "user_" + userBase.getId());
        }
        return "123";
    }

    @GetMapping(value = "/update/movie")
    public String UpdateMovie() throws Exception {
        File linksFile = new File("recommend_service/src/main/resources/image/links.csv");
        HashMap<String,String> hashMap = new HashMap<>();
        try {
             //  初始化数据，通过 tmdbId 获取 movieId
            BufferedReader linksReader = new BufferedReader(new FileReader(linksFile));
            linksReader.readLine(); //  读取首行信息
            String linksLine = null;
            while ((linksLine = linksReader.readLine()) != null){
                String[] links = linksLine.split(",",-1);
                if(links[0] == null || links[2] == null){
                    log.info("null data");
                }
                hashMap.put(links[2],links[0]); //  顺序为 :: tmdbId :: movieId
            }
            log.info("hash done " + hashMap.size());
        }catch (Exception e){
            return "ERROR";
        }
        File imageUrlFile = new File("recommend_service/src/main/resources/image/movie_path.csv");
        try {
            BufferedReader imageUrlReader = new BufferedReader(new FileReader(imageUrlFile));
            String imageUrlLine = null;
            while ((imageUrlLine = imageUrlReader.readLine()) != null) {
                String item[] = imageUrlLine.split(",", -1);//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String tmdbId = item[0];
                String imageUrl = "https://image.tmdb.org"+item[1];
                if(imageUrl == null){
                    log.info("imageUrl == null");
                }
                if(!hashMap.containsKey(tmdbId)){
                    log.info("cant find key");
                }
                String movieId = hashMap.get(tmdbId);
                ItemInfo itemInfo = itemInfoMapper.selectByPrimaryKey(Integer.parseInt(movieId));
                itemInfo.setImageUrl(imageUrl);
                itemInfoMapper.updateByPrimaryKey(itemInfo);
            }
            log.info("update done");
        } catch (Exception e) {
            return e.toString();
        }
        File overviewFile = new File("recommend_service/src/main/resources/image/movie_overview_path.csv");
        try {
            BufferedReader overviewReader = new BufferedReader(new FileReader(overviewFile));
            String overviewLine = null;
            int count = 10722;
            int pos = 1;
            while ((overviewLine = overviewReader.readLine()) != null && pos < count) {
                String item[] = overviewLine.split(",", -1);//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                String tmdbId = item[0];
                String overview = "";
                if(item.length < 2){
                    log.info("length error :: "+ item);
                }
                for (int i = 1; i <= item.length - 1; i++) {
                    overview = overview + item[i];
                }
                if(overview == null){
                    log.info("overview == null");
                }
                if(!hashMap.containsKey(tmdbId)){
                    log.info("cant find key");
                }
                String movieId = hashMap.get(tmdbId);
                ItemInfo itemInfo = itemInfoMapper.selectByPrimaryKey(Integer.parseInt(movieId));
                itemInfo.setOverview(overview);
                itemInfoMapper.updateByPrimaryKey(itemInfo);
                pos ++;
            }
            log.info("update done");
        } catch (Exception e) {
            return e.toString();
        }
        return "done!";
    }
}
