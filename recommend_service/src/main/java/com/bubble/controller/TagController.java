package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.bubble.vo.TagDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/24 7:40 下午
 * @Desc :
 */
@Slf4j
@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/tag")
public class TagController {
    @GetMapping("/detail-to-movie")
    public JSON getTagDetailByMovieId(@RequestParam(value = "movieId") int movieId){
        List<TagDetail> tagDetailList = new ArrayList<>();
        for(int i = 0;i < 3;i ++){
            TagDetail tagDetail = new TagDetail();
            tagDetail.setTagId("77"+i);
            tagDetail.setTagColor("red");
            tagDetail.setTagName("我的小tag_"+i);
            tagDetail.setTagImgUrl("https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces/3Rfvhy1Nl6sSGJwyjb0QiZzZYlB.jpg");
            tagDetailList.add(tagDetail);
        }
        log.info(String.valueOf((JSON) JSON.toJSON(tagDetailList)));
        return (JSON) JSON.toJSON(tagDetailList);
    }
    @GetMapping("/detail")
    public JSON getTagDetailByTagId(@RequestParam(value = "tagId")String tagId){
        TagDetail tagDetail = new TagDetail();
        tagDetail.setTagId("520");
        tagDetail.setTagColor("red");
        tagDetail.setTagName("我的小tag_"+520);
        tagDetail.setTagImgUrl("https://www.themoviedb.org/t/p/w1920_and_h800_multi_faces/3Rfvhy1Nl6sSGJwyjb0QiZzZYlB.jpg");
        log.info(String.valueOf((JSON) JSON.toJSON(tagDetail)));
        return (JSON) JSON.toJSON(tagDetail);
    }
}
