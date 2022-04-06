package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.bubble.mapper.ItemInfoMapper;
import com.bubble.mapper.UserBaseMapper;
import com.bubble.mapper.UserRecommendMapper;
import com.bubble.model.*;
import com.bubble.service.ItemService;
import com.bubble.service.impl.UserDetailsServiceImpl;
import com.bubble.vo.MovieDetail;
import com.bubble.vo.ResponseEntity;
import com.bubble.vo.user.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/24 4:52 下午
 * @Desc :
 */
@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    private ItemService itemService;
    @Resource
    private UserRecommendMapper userRecommendMapper;
    @Resource
    private UserBaseMapper userBaseMapper;
    @Resource
    private ItemInfoMapper itemInfoMapper;

    @GetMapping("/detail")
    public JSON getMovieDetail(@RequestParam(value = "id") String movieId) {
        MovieDetail movieDetail = new MovieDetail();
        ItemInfo itemInfo = itemInfoMapper.selectByPrimaryKey(Integer.parseInt(movieId));
        if (itemInfo == null) {
            //  异常处理
            log.info("ERROR :: get null ItemInfo !");
            return (JSON) JSON.toJSON(movieDetail);
        }
        //  构造返回值
        movieDetail.setMovieId(movieId);
        movieDetail.setMovieName(itemInfo.getName());
        movieDetail.setImgUrl(itemInfo.getImageUrl());
        movieDetail.setMovieQuote(itemInfo.getOverview());
        log.info(String.valueOf((JSON) JSON.toJSON(movieDetail)));
        return (JSON) JSON.toJSON(movieDetail);
    }

    @GetMapping("/tag")
    public JSON MovieDetailByTagId(@RequestParam(value = "tagId") String tagId) {
        List<MovieDetail> movieDetailList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            MovieDetail movieDetail = new MovieDetail();
            movieDetail.setMovieId("7758" + i);
            movieDetail.setMovieName("这是movie_" + i);
            movieDetail.setMovieQuote("这是评论_" + i);
            movieDetail.setImgUrl("https://image.tmdb.org/t/p/w300_and_h450_bestv2/61yu1ejOBWUrrMRplRbjpvxgxVc.jpg");
            movieDetailList.add(movieDetail);
        }
        log.info(String.valueOf((JSON) JSON.toJSON(movieDetailList)));
        return (JSON) JSON.toJSON(movieDetailList);
    }

    @GetMapping("/recommend")
    public JSON MovieRecommendByUser(@RequestParam(value = "page") String page) {
        //  获取用户登陆信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            //  用户已登陆
            log.info("MovieRecommendByUser :: user is not with AnonymousAuthenticationToken");
        } else {
            //  用户未登陆
            log.info("MovieRecommendByUser :: get AnonymousAuthenticationToken");
        }
        if (authentication.getPrincipal() != null) {
            //  获取Token中用户信息
            log.info("MovieRecommendByUser :: user not null");
            try {
                User user = (User) authentication.getPrincipal();
                String username = user.getUsername();
                log.info("MovieRecommendByUser :: " + username);
                UserBaseExample userBaseExample = new UserBaseExample();
                userBaseExample.createCriteria().andNameEqualTo(username);
                List<UserBase> userBaseList = userBaseMapper.selectByExample(userBaseExample);
                log.info("MovieRecommendByUser :: userBaseList size:: " + userBaseList.size());
                //  无法查找用户信息时兜底逻辑-返回默认列表
                if (userBaseList.size() < 1) {
                    log.info("cant find user error, return default page");
                    return DefaultMovieRecommend(page);
                }
                //  获取推荐列表
                List<MovieDetail> movieDetailList = itemService.getMovieRecommendByUser(userBaseList.get(0).getId(), page);
                log.info("MovieRecommendByUser :: return value :: " + String.valueOf((JSON) JSON.toJSON(movieDetailList)));
                return (JSON) JSON.toJSON(movieDetailList);
            } catch (Exception e) {
                log.info(e.toString());
                log.info("MovieRecommendByUser Exception :: no token error return default");
                return DefaultMovieRecommend(page);
            }
        }
        //  Token中无法解析用户信息时返回默认列表
        log.info("ERROR :: authentication.getPrincipal() == null");
        return DefaultMovieRecommend(page);
    }


    public JSON DefaultMovieRecommend(String page) {
        List<MovieDetail> movieDetailList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            MovieDetail movieDetail = new MovieDetail();
            movieDetail.setMovieId("7758" + i);
            movieDetail.setMovieName("这是movie_" + i);
            movieDetail.setMovieQuote("这是评论_" + i);
            movieDetail.setImgUrl("https://image.tmdb.org/t/p/w300_and_h450_bestv2/hbX0bBHTFThyChUel3INrvEZiFF.jpg");
            movieDetailList.add(movieDetail);
        }
        log.info(String.valueOf((JSON) JSON.toJSON(movieDetailList)));
        return (JSON) JSON.toJSON(movieDetailList);
    }
}
