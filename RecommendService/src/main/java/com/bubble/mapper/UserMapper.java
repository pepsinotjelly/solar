package com.bubble.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bubble.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/7 10:45 AM
 * @Desc:
 */
@Mapper
//@Repository
@Component
public interface UserMapper extends BaseMapper<User> {
}
