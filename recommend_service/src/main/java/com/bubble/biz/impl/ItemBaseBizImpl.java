package com.bubble.biz.impl;

import com.bubble.biz.ItemBaseBiz;
import com.bubble.service.ItemBaseService;
import com.bubble.model.ItemBase;
import com.bubble.thrift.ThriftProxyFactory;
import com.bubble.thrift.recommend_service.*;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemBaseBizImpl implements ItemBaseBiz {
    @Autowired
    private ItemBaseService itemBaseService;

    private final String[] hostPorts = new String[]{"localhost:7090"};
    private RecommendService.Iface client = (RecommendService.Iface) ThriftProxyFactory.newInstance(RecommendService.class, hostPorts);


    public boolean SyncItemBase() throws TException {
        SyncItemBaseRequest request = new SyncItemBaseRequest();
        //  读取数据\拼接数据
        List<ItemBase> itemBaseList = itemBaseService.batchGetItemBase(1, 100);
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

    @Override
    public String GetRecommendInfo(GetRecommendInfoRequest request) throws TException {
        GetRecommendInfoResponse getRecommendInfoResponse = client.GetRecommendInfo(request);
        // TODO
        //  初始化矩阵 A
        //  解析矩阵AB
        //  解析矩阵BB
        //  计算余弦相似度
        return "done";
    }
}
