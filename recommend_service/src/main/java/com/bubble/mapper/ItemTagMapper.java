package com.bubble.mapper;

import com.bubble.model.ItemTag;
import com.bubble.model.ItemTagExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemTagMapper {
    long countByExample(ItemTagExample example);

    int deleteByExample(ItemTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ItemTag record);

    int insertSelective(ItemTag record);

    List<ItemTag> selectByExample(ItemTagExample example);

    ItemTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ItemTag record, @Param("example") ItemTagExample example);

    int updateByExample(@Param("record") ItemTag record, @Param("example") ItemTagExample example);

    int updateByPrimaryKeySelective(ItemTag record);

    int updateByPrimaryKey(ItemTag record);
}