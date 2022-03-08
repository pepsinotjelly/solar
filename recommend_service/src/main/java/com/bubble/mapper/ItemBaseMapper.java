package com.bubble.mapper;

import com.bubble.model.ItemBase;
import com.bubble.model.ItemBaseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemBaseMapper {
    long countByExample(ItemBaseExample example);

    int deleteByExample(ItemBaseExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ItemBase record);

    int insertSelective(ItemBase record);

    List<ItemBase> selectByExample(ItemBaseExample example);

    ItemBase selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ItemBase record, @Param("example") ItemBaseExample example);

    int updateByExample(@Param("record") ItemBase record, @Param("example") ItemBaseExample example);

    int updateByPrimaryKeySelective(ItemBase record);

    int updateByPrimaryKey(ItemBase record);
}