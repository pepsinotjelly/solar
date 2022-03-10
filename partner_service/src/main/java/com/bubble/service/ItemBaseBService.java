package com.bubble.service;

import com.bubble.model.ItemBaseB;
import com.bubble.thrift.recommend_service.SyncItemBaseRequest;
import com.bubble.thrift.recommend_service.SyncItemBaseResponse;
import org.apache.thrift.TException;

import java.util.List;

public interface ItemBaseBService {
    List<ItemBaseB> BatchGetItemBaseB(int startPosition,int endPosition);
    List<ItemBaseB> getItemBaseB(int itemId);
    SyncItemBaseResponse SyncItemBase(SyncItemBaseRequest syncItemBaseRequest) throws TException;

}
