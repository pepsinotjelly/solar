package com.bubble.controller;

import com.alibaba.fastjson.JSON;
import com.bubble.mapper.ItemTagMapper;
import com.bubble.mapper.TagMapper;
import com.bubble.model.ItemTag;
import com.bubble.model.ItemTagExample;
import com.bubble.model.Tag;
import com.bubble.model.TagExample;
import com.bubble.vo.TagDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @Resource
    private TagMapper tagMapper;
    @Resource
    private ItemTagMapper itemTagMapper;

    @GetMapping("/detail-to-movie")
    public JSON getTagDetailByMovieId(@RequestParam(value = "movieId") int movieId) {
        //  通过电影id获取tag列表
        ItemTagExample itemTagExample = new ItemTagExample();
        itemTagExample.createCriteria().andItemIdEqualTo(movieId);
        List<ItemTag> itemTagList = itemTagMapper.selectByExample(itemTagExample);
        log.info("itemTagList :: "+itemTagList);
        //  获取 tagId 列表
        List<Integer> itemIdList = new ArrayList<>();
        for(ItemTag itemTag:itemTagList){
            itemIdList.add(itemTag.getTagId());
        }
        //  通过 id 列表获取 tag 信息
        TagExample tagExample = new TagExample();
        tagExample.createCriteria().andIdIn(itemIdList);
        List<Tag> tagList = tagMapper.selectByExample(tagExample);
        //  格式化数据
        List<TagDetail> tagDetailList = new ArrayList<>();
        for(Tag tag : tagList){
            TagDetail tagDetail = new TagDetail();
            tagDetail.setTagName(tag.getName());
            tagDetail.setTagId(tag.getId().toString());
            tagDetail.setTagColor(tag.getColor());
            tagDetail.setTagImgUrl(tag.getImgUrl());
            tagDetailList.add(tagDetail);
        }
        log.info(String.valueOf((JSON) JSON.toJSON(tagDetailList)));
        return (JSON) JSON.toJSON(tagDetailList);
    }

    @GetMapping("/detail")
    public JSON getTagDetailByTagId(@RequestParam(value = "tagId") String tagId) {
        TagDetail tagDetail = new TagDetail();
        Tag tag = tagMapper.selectByPrimaryKey(Integer.parseInt(tagId));
        tagDetail.setTagId(tag.getId().toString());
        tagDetail.setTagColor(tag.getColor());
        tagDetail.setTagName(tag.getName());
        tagDetail.setTagImgUrl(tag.getImgUrl());
        log.info(String.valueOf((JSON) JSON.toJSON(tagDetail)));
        return (JSON) JSON.toJSON(tagDetail);
    }
}
