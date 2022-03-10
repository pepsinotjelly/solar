package com.bubble.service;

import com.bubble.model.ItemBaseB;
import com.bubble.thrift.recommend_service.SyncItemBaseRequest;
import org.apache.thrift.TException;

import java.util.List;

public interface ItemBaseBService {
    List<ItemBaseB> BatchGetItemBaseB(int startPosition,int endPosition);
    List<ItemBaseB> getItemBaseB(int itemId);
    Integer insert(ItemBaseB itemBaseB);
    Integer update(ItemBaseB itemBaseB);
    boolean SyncItemBase(SyncItemBaseRequest syncItemBaseRequest) throws TException;

}
