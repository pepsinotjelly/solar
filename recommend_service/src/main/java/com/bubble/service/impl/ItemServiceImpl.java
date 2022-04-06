package com.bubble.service.impl;

import com.bubble.mapper.ItemInfoMapper;
import com.bubble.mapper.UserRecommendMapper;
import com.bubble.model.*;
import com.bubble.service.ItemService;
import com.bubble.mapper.ItemBaseMapper;
import com.bubble.thrift.ThriftProxyFactory;
import com.bubble.thrift.recommend_service.ItemBaseEntity;
import com.bubble.thrift.recommend_service.RecommendService;
import com.bubble.thrift.recommend_service.SyncItemBaseRequest;
import com.bubble.thrift.recommend_service.SyncItemBaseResponse;
import com.bubble.vo.MovieDetail;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService {
    @Resource
    private ItemBaseMapper itemBaseMapper;
    @Resource
    private UserRecommendMapper userRecommendMapper;
    @Resource
    private ItemInfoMapper itemInfoMapper;

    private final String[] hostPorts = new String[]{"localhost:7090"};
    private RecommendService.Iface client = (RecommendService.Iface) ThriftProxyFactory.newInstance(RecommendService.class, hostPorts);

    @Override
    public Integer insert(ItemBase itemBase) {
        return itemBaseMapper.insert(itemBase);
    }


    @Override
    public List<ItemBase> batchGetItemBase(int startPosition, int endPosition) {
        ItemBaseExample itemBaseExample = new ItemBaseExample();
        itemBaseExample.createCriteria().andIdBetween(startPosition, endPosition);
        return itemBaseMapper.selectByExample(itemBaseExample);
    }

    @Override
    public boolean SyncItemBase() throws TException {
        SyncItemBaseRequest request = new SyncItemBaseRequest();
        //  读取数据\拼接数据
        List<ItemBase> itemBaseList = this.batchGetItemBase(1, 200000);
        List<ItemBaseEntity> itemBaseEntities = new ArrayList<>();
        for (ItemBase item : itemBaseList) {
            ItemBaseEntity entity = new ItemBaseEntity();
            entity.setId(item.getId());
            entity.setTitle(item.getTitle());
            itemBaseEntities.add(entity);
        }
        request.setItemInfoEntityList(itemBaseEntities);
        SyncItemBaseResponse syncItemBaseResponse = client.SyncItemBase(request);
        return syncItemBaseResponse.done;
    }

    @Override
    public List<MovieDetail> getMovieRecommendByUser(int userId, String page) {
        //  根据用户id获取用户的推荐信息
        UserRecommendExample userRecommendExample = new UserRecommendExample();
        userRecommendExample.createCriteria().andUserIdEqualTo(userId);
        List<UserRecommend> userRecommendList = userRecommendMapper.selectByExample(userRecommendExample);
        List<Integer> recommendMovieIdList = new ArrayList<>();
        //  格式化每页最多4个推荐内容-过滤item id
        for (int i = 0; i < 4; i++) {
            int movieId = userRecommendList.get(((Integer.parseInt(page) - 1) * 4 + i) % userRecommendList.size()).getItemId();
            log.info("movieId :: " + movieId);
            recommendMovieIdList.add(movieId);
        }
        return getMovieDetail(recommendMovieIdList);
    }

    @Override
    public List<MovieDetail> getMovieDetail(List<Integer> recommendMovieIdList) {
        //  通过电影id列表找到对应电影信息
        log.info("MovieRecommendByUser :: recommendMovieIdList :: " + recommendMovieIdList);
        ItemInfoExample itemInfoExample = new ItemInfoExample();
        itemInfoExample.createCriteria().andIdIn(recommendMovieIdList);
        List<MovieDetail> movieDetailList = new ArrayList<>();
        List<ItemInfo> itemInfoList = itemInfoMapper.selectByExample(itemInfoExample);
        //  构造返回值
        for (ItemInfo info : itemInfoList) {
            MovieDetail movieDetail = new MovieDetail();
            movieDetail.setMovieId(info.getId().toString());
            movieDetail.setMovieName(info.getName());
            movieDetail.setMovieQuote(info.getOverview());
            movieDetail.setImgUrl(info.getImageUrl());
            movieDetailList.add(movieDetail);
        }
        return movieDetailList;
    }
}
