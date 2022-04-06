package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.bubble.mapper.ItemInfoMapper;
import com.bubble.mapper.UserBaseMapper;
import com.bubble.mapper.UserRecommendMapper;
import com.bubble.model.*;
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
    @Resource
    private UserRecommendMapper userRecommendMapper;
    @Resource
    private UserBaseMapper userBaseMapper;
    @Resource
    private ItemInfoMapper itemInfoMapper;

    @GetMapping("/detail")
    public JSON getMovieDetail(@RequestParam(value = "id") String movieId) {
        MovieDetail movieDetail = new MovieDetail();
        movieDetail.setMovieId(movieId);
        movieDetail.setMovieName("来自service的名字");
        movieDetail.setImgUrl("https://image.tmdb.org/t/p/w300_and_h450_bestv2/zGdGINvf9oSysprbWnu85dIM7rc.jpg");
        movieDetail.setMovieQuote("一段简介：作为我的电影简介吧");
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
            // 登入用户
            log.info("MovieRecommendByUser :: user is not with AnonymousAuthenticationToken");
        } else {
            log.info("MovieRecommendByUser :: get AnonymousAuthenticationToken");
        }
        if (authentication.getPrincipal() != null) {
            log.info("MovieRecommendByUser :: user not null");
            try {
                User user = (User) authentication.getPrincipal();
                String username = user.getUsername();
                log.info("MovieRecommendByUser :: " + username);
                UserBaseExample userBaseExample = new UserBaseExample();
                userBaseExample.createCriteria().andNameEqualTo(username);
                List<UserBase> userBaseList = userBaseMapper.selectByExample(userBaseExample);
                log.info("MovieRecommendByUser :: userBaseList size:: "+ userBaseList.size());
                if (userBaseList.size() < 1) {
                    log.info("cant find user error, return default page");
                    return DefaultMovieRecommend(page);
                }
                UserRecommendExample userRecommendExample = new UserRecommendExample();
                userRecommendExample.createCriteria().andUserIdEqualTo(userBaseList.get(0).getId());
                List<UserRecommend> userRecommendList = userRecommendMapper.selectByExample(userRecommendExample);
                List<Integer> recommendMovieIdList = new ArrayList<>();
                //  格式化每页最多4个推荐内容
                for (int i = 0; i < 4; i++) {
                    int movieId = userRecommendList.get(((Integer.parseInt(page) - 1) * 4 +i)% userRecommendList.size()).getItemId();
                    log.info("movieId :: " + movieId);
                    recommendMovieIdList.add(movieId);
                }
                log.info("MovieRecommendByUser :: recommendMovieIdList :: " + recommendMovieIdList);
                ItemInfoExample itemInfoExample = new ItemInfoExample();
                itemInfoExample.createCriteria().andIdIn(recommendMovieIdList);
                List<MovieDetail> movieDetailList = new ArrayList<>();
                List<ItemInfo> itemInfoList = itemInfoMapper.selectByExample(itemInfoExample);
                for (ItemInfo info : itemInfoList) {
                    MovieDetail movieDetail = new MovieDetail();
                    movieDetail.setMovieId(info.getId().toString());
                    movieDetail.setMovieName(info.getName());
                    movieDetail.setMovieQuote(info.getOverview());
                    movieDetail.setImgUrl(info.getImageUrl());
                    movieDetailList.add(movieDetail);
                }
                log.info("MovieRecommendByUser :: return value :: " + String.valueOf((JSON) JSON.toJSON(movieDetailList)));
                return (JSON) JSON.toJSON(movieDetailList);
            } catch (Exception e) {
                log.info(e.toString());
                log.info("MovieRecommendByUser Exception :: no token error return default");
                return DefaultMovieRecommend(page);
            }
        }
        log.info("ERROR :: authentication.getPrincipal() == null");
        List<MovieDetail> movieDetailList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
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
