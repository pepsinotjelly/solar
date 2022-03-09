package com.bubble.mapper;

import com.bubble.model.ItemBaseB;
import com.bubble.model.ItemBaseBExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ItemBaseBMapper {
    long countByExample(ItemBaseBExample example);

    int deleteByExample(ItemBaseBExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ItemBaseB record);

    int insertSelective(ItemBaseB record);

    List<ItemBaseB> selectByExample(ItemBaseBExample example);

    ItemBaseB selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ItemBaseB record, @Param("example") ItemBaseBExample example);

    int updateByExample(@Param("record") ItemBaseB record, @Param("example") ItemBaseBExample example);

    int updateByPrimaryKeySelective(ItemBaseB record);

    int updateByPrimaryKey(ItemBaseB record);
}