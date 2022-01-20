package com.bubble.mapper;

import com.bubble.model.CommodityInfo;
import com.bubble.model.CommodityInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommodityInfoMapper {
    long countByExample(CommodityInfoExample example);

    int deleteByExample(CommodityInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(CommodityInfo record);

    int insertSelective(CommodityInfo record);

    List<CommodityInfo> selectByExampleWithBLOBs(CommodityInfoExample example);

    List<CommodityInfo> selectByExample(CommodityInfoExample example);

    CommodityInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CommodityInfo record, @Param("example") CommodityInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") CommodityInfo record, @Param("example") CommodityInfoExample example);

    int updateByExample(@Param("record") CommodityInfo record, @Param("example") CommodityInfoExample example);

    int updateByPrimaryKeySelective(CommodityInfo record);

    int updateByPrimaryKeyWithBLOBs(CommodityInfo record);

    int updateByPrimaryKey(CommodityInfo record);
}