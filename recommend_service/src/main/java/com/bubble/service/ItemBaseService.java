package com.bubble.service;

import com.bubble.model.ItemBase;

import java.util.List;

public interface ItemBaseService {
    Integer insert(ItemBase itemBase);
    Integer update(ItemBase itemBase);
    List<ItemBase> batchGetItemBase(int startPosition,int endPosition);
}
