package com.bubble.service.impl;

import com.bubble.mapper.UserBaseMapper;
import com.bubble.model.UserBase;
import com.bubble.model.UserBaseExample;
import com.bubble.vo.user.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/30 8:10 下午
 * @Desc :
 */
@Slf4j
@Component
public class MyCustomUserService implements UserDetailsService {

    @Resource
    private com.bubble.mapper.UserBaseMapper userBaseMapper;

    /**
     * 登陆验证时，通过username获取用户的所有权限信息
     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBaseExample example = new UserBaseExample();
        example.createCriteria().andNameEqualTo(username);
        MyUserDetails myUserDetails = new MyUserDetails();
        log.info("MyCustomUserService :: loadUserByUsername :: username :: "+username);
        myUserDetails.setUsername(username);
        UserBase userBase = userBaseMapper.selectByExample(example).get(0);
        myUserDetails.setPassword(userBase.getPassword());
        log.info("MyCustomUserService :: loadUserByUsername :: password"+userBase.getPassword());
        return myUserDetails;
    }
}