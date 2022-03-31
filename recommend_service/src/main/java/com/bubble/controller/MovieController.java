package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.bubble.vo.MovieDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
    public JSON MovieRecommendByUserId(@RequestParam(value = "page") String page) {
        //  获取用户登陆信息
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken){
            //  返回默认demo信息
            return DefaultMovieRecommend(page);
        }
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
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
