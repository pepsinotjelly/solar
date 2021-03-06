package com.bubble.service.impl;

import com.alibaba.fastjson.JSON;
import com.bubble.mapper.*;
import com.bubble.model.*;
import com.bubble.service.UserService;
import com.bubble.vo.user.UserEntity;
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
    private UserInfoMapper userInfoMapper;
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
    public String getMyRecommend(int user_id) throws Exception {
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
    public Integer rateMovie(int user_id, String rate, int movie_id, String comment) throws Exception {
        RatingRecord ratingRecord = new RatingRecord();
        ratingRecord.setRating(Double.parseDouble(rate));
        ratingRecord.setUserId(user_id);
        ratingRecord.setItemId(movie_id);
        ratingRecord.setComment(comment);
        return ratingRecordMapper.insert(ratingRecord);
    }


    @Override
    public UserEntity findUserEntityByUsername(String username) throws Exception {
        UserEntity userEntity = new UserEntity();
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andNameEqualTo(username);
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        if(userInfoList.isEmpty()){
            log.info("findUserEntityByUsername :: cant find user !!");
            return userEntity;
        }
        UserInfo userInfo  = userInfoList.get(0);
        if(userInfo != null){
            userEntity.setUserId(userInfo.getId().toString());
            userEntity.setUserName(userInfo.getName());
            userEntity.setUserAvatar(userInfo.getAvatarcolor());
            userEntity.setUserEmail(userInfo.getEmail());
        }
        return userEntity;
    }
/**
    @Override
    public User add(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword())); //对密码进行加密
        User user2 = userRepository.save(user);
        return user2;
    }

    @Override
    public ResultInfo login(User user) {
        ResultInfo resultInfo=new ResultInfo();
        User user2 = userRepository.findByName(user.getName());
        if (user2==null) {
            resultInfo.setCode("-1");
            resultInfo.setMessage("用户名不存在");
            return resultInfo;
        }

        //判断密码是否正确
        if (!bCryptPasswordEncoder.matches(user.getPassword(),user2.getPassword())) {
            resultInfo.setCode("-1");
            resultInfo.setMessage("密码不正确");
            return resultInfo;
        }
        resultInfo.setMessage("登录成功");
        return resultInfo;
    }

 ******/


}
