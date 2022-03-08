package com.bubble.dal.impl;

import com.bubble.dal.ItemBaseService;
import com.bubble.mapper.ItemBaseMapper;
import com.bubble.mapper.UserBaseMapper;
import com.bubble.model.ItemBase;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemBaseServiceImpl implements ItemBaseService {
    @Resource
    private ItemBaseMapper itemBaseMapper;
    @Override
    public List<ItemBase> getList() {
        return null;
    }

    @Override
    public Integer register(ItemBase itemBase) {
        return null;
    }

    @Override
    public Integer login(ItemBase itemBase) {
        return null;
    }

    @Override
    public Integer insert(ItemBase itemBase) {
        return itemBaseMapper.insert(itemBase);
    }
}
