package com.bubble.service.impl;

import com.bubble.service.ItemBaseService;
import com.bubble.mapper.ItemBaseMapper;
import com.bubble.model.ItemBase;
import com.bubble.model.ItemBaseExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ItemBaseServiceImpl implements ItemBaseService {
    @Resource
    private ItemBaseMapper itemBaseMapper;

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
}
