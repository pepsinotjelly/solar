package com.bubble.service.impl;

import com.alibaba.fastjson.JSON;
import com.bubble.mapper.ItemInfoMapper;
import com.bubble.mapper.RatingRecordMapper;
import com.bubble.mapper.UserRecommendMapper;
import com.bubble.model.*;
import com.bubble.service.UserService;
import com.bubble.mapper.UserBaseMapper;
import com.bubble.vo.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/25 7:59 PM
 * @Desc:
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserBaseMapper userBaseMapper;
    @Resource
    private ItemInfoMapper itemInfoMapper;
    @Resource
    private UserRecommendMapper userRecommendMapper;
    @Resource
    private RatingRecordMapper ratingRecordMapper;

    @Override
    public Integer register(UserBase userBase) {
        //TODO check_data
        userBaseMapper.insert(userBase);
        return 0;
    }

    @Override
    public Integer login(UserBase userBase) {
        return null;
    }

    @Override
    public String getMyRecommend(int user_id) throws Exception{
        //  查询用户推荐表
        UserRecommendExample userRecommendExample = new UserRecommendExample();
        userRecommendExample.createCriteria().andUserIdEqualTo(user_id);
        List<UserRecommend> userRecommendList = userRecommendMapper.selectByExample(userRecommendExample);
        List<Integer> itemIdList = new ArrayList<>();
        //  拼接itemId列表，通过id查询itemInfo
        for (UserRecommend u : userRecommendList) {
            itemIdList.add(u.getItemId());
        }
        log.info("ItemInfo");
        //  查询ItemInfo
        ItemInfoExample itemInfoExample = new ItemInfoExample();
        itemInfoExample.createCriteria().andIdIn(itemIdList);
        List<ItemInfo> itemInfoList = itemInfoMapper.selectByExample(itemInfoExample);
        //  构造返回值
        String itemInfoJson = JSON.toJSONString(itemInfoList);
        log.info("RESPONSE :: getMyRecommend :: " + itemInfoJson);
        return itemInfoJson;
    }

    @Override
    public Integer rateMovie(int user_id, String rate, int movie_id ,String comment) throws Exception{
        RatingRecord ratingRecord = new RatingRecord();
        ratingRecord.setRating(Double.parseDouble(rate));
        ratingRecord.setUserId(user_id);
        ratingRecord.setItemId(movie_id);
        ratingRecord.setComment(comment);
        return ratingRecordMapper.insert(ratingRecord);
    }

    @Override
    public UserEntity findUserById(String id) throws Exception {
        UserEntity user = new UserEntity();
        user.setUserAvatar("https://image.tmdb.org/t/p/w300_and_h450_bestv2/bNeE1kUMFYG1sG6blHMwkG9sXXM.jpg");
        user.setUserName("name_test");
        user.setUserId(id);
        user.setUserPwd("abcd");
        log.info("findUserById :: "+id);
        return user;
    }
}
