package com.bubble.dal;

import com.bubble.model.ItemBase;
import com.bubble.model.UserBase;

import java.util.List;

public interface ItemBaseService {
    List<ItemBase> getList();
    Integer register(ItemBase itemBase);
    Integer login(ItemBase itemBase);
    Integer insert(ItemBase itemBase);
}
