package com.bubble.mapper;

import com.bubble.model.ImageInfo;
import com.bubble.model.ImageInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ImageInfoMapper {
    long countByExample(ImageInfoExample example);

    int deleteByExample(ImageInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ImageInfo record);

    int insertSelective(ImageInfo record);

    List<ImageInfo> selectByExample(ImageInfoExample example);

    ImageInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ImageInfo record, @Param("example") ImageInfoExample example);

    int updateByExample(@Param("record") ImageInfo record, @Param("example") ImageInfoExample example);

    int updateByPrimaryKeySelective(ImageInfo record);

    int updateByPrimaryKey(ImageInfo record);
}