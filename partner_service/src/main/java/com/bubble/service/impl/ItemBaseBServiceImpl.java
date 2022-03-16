package com.bubble.service.impl;

import com.bubble.service.ItemBaseBService;
import com.bubble.mapper.ItemBaseBMapper;
import com.bubble.model.ItemBaseB;
import com.bubble.model.ItemBaseBExample;
import com.bubble.thrift.BaseResp;
import com.bubble.thrift.recommend_service.ItemBaseEntity;
import com.bubble.thrift.recommend_service.SyncItemBaseRequest;
import com.bubble.thrift.recommend_service.SyncItemBaseResponse;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemBaseBServiceImpl implements ItemBaseBService {
    @Resource
    private ItemBaseBMapper itemBaseBMapper;

    @Override
    public List<ItemBaseB> BatchGetItemBaseB(int startPosition, int endPosition) {
        ItemBaseBExample example = new ItemBaseBExample();
        example.createCriteria().andIdBetween(startPosition, endPosition);
        List<ItemBaseB> itemBaseBList = itemBaseBMapper.selectByExample(example);
        return itemBaseBList;
    }

    @Override
    public List<ItemBaseB> getItemBaseB(int itemId) {
        ItemBaseBExample example = new ItemBaseBExample();
        example.createCriteria().andIdEqualTo(itemId);
        return itemBaseBMapper.selectByExample(example);
    }


    @Override
    public SyncItemBaseResponse SyncItemBase(SyncItemBaseRequest syncItemBaseRequest) throws TException {
        //  读取信息
        SyncItemBaseResponse response = new SyncItemBaseResponse();
        List<ItemBaseEntity> entityList = syncItemBaseRequest.getItemInfoEntityList();
        List<ItemBaseB> itemBaseBList = new ArrayList<>();
        if (entityList == null) {
            response.setDone(false);
            response.setBaseResp(new BaseResp().setStatusCode(10003));
            return response;
        }
        for (ItemBaseEntity entity : entityList) {
            ItemBaseB itemBaseB = new ItemBaseB();
            itemBaseB.setId(entity.getId());
            itemBaseB.setName(entity.getTitle());
            itemBaseBList.add(itemBaseB);
        }
        //  对比数据库中信息,写入数据库
        for (ItemBaseB itemBaseB : itemBaseBList) {
            List<ItemBaseB> currentItem = this.getItemBaseB(itemBaseB.getId());
            if (currentItem == null || currentItem.isEmpty()) {
                itemBaseBMapper.insert(itemBaseB);
            } else {
                itemBaseBMapper.updateByPrimaryKey(itemBaseB);
            }
        }
        // 构造返回值
        response.setDone(true);
        BaseResp baseResp = new BaseResp();
        baseResp.setStatusCode(0);
        response.setBaseResp(baseResp);
        return response;
    }
}
