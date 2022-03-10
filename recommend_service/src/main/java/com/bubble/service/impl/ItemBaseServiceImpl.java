package com.bubble.service.impl;

import com.bubble.service.ItemBaseService;
import com.bubble.mapper.ItemBaseMapper;
import com.bubble.model.ItemBase;
import com.bubble.model.ItemBaseExample;
import com.bubble.thrift.ThriftProxyFactory;
import com.bubble.thrift.recommend_service.ItemBaseEntity;
import com.bubble.thrift.recommend_service.RecommendService;
import com.bubble.thrift.recommend_service.SyncItemBaseRequest;
import com.bubble.thrift.recommend_service.SyncItemBaseResponse;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemBaseServiceImpl implements ItemBaseService {
    @Resource
    private ItemBaseMapper itemBaseMapper;

    private final String[] hostPorts = new String[]{"localhost:7090"};
    private RecommendService.Iface client = (RecommendService.Iface) ThriftProxyFactory.newInstance(RecommendService.class, hostPorts);

    @Override
    public Integer insert(ItemBase itemBase) {
        return itemBaseMapper.insert(itemBase);
    }

    @Override
    public Integer update(ItemBase itemBase) {
        return itemBaseMapper.updateByPrimaryKey(itemBase);
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
            entity.setGenres(item.getGenres());
            entity.setTitle(item.getTitle());
            itemBaseEntities.add(entity);
        }
        request.setItemInfoEntityList(itemBaseEntities);
        SyncItemBaseResponse syncItemBaseResponse = client.SyncItemBase(request);
        return syncItemBaseResponse.done;
    }
}
