package com.bubble.service.impl;

import com.bubble.mapper.UserBaseMapper;
import com.bubble.model.UserBase;
import com.bubble.model.UserBaseExample;
import com.bubble.vo.user.MyUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/30 8:10 下午
 * @Desc :
 */
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private com.bubble.mapper.UserBaseMapper userBaseMapper;

    /**
     * 登陆验证时，通过username获取用户的所有权限信息
     * 并返回UserDetails放到spring的全局缓存SecurityContextHolder中，以供授权器使用
     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserBaseExample example = new UserBaseExample();
//        example.createCriteria().andNameEqualTo(username);
//        MyUserDetails myUserDetails = new MyUserDetails();
//        log.info("MyCustomUserService :: loadUserByUsername :: username :: " + username);
//        myUserDetails.setUsername(username);
//        //获取用户权限，并把其添加到GrantedAuthority中
//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
//        grantedAuthorities.add(grantedAuthority);
//        myUserDetails.setAuthorities(grantedAuthorities);
//        log.info("set role :: " + myUserDetails.getAuthorities());
//        List<UserBase> userBaseList = userBaseMapper.selectByExample(example);
//        if (userBaseList.isEmpty()) {
//            throw new UsernameNotFoundException("user name invalid");
//        }
//        UserBase userBase = userBaseList.get(0);
//        myUserDetails.setPassword(userBase.getPassword());
//        log.info("loadUserByUsername :: password :: " + userBase.getPassword());
//        return myUserDetails;
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBaseExample example = new UserBaseExample();
        example.createCriteria().andNameEqualTo(username);
        log.info("loadUserByUsername :: username :: " + username);
        //获取用户权限，并把其添加到GrantedAuthority中
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("USER");
        grantedAuthorities.add(grantedAuthority);
        List<UserBase> userBaseList = userBaseMapper.selectByExample(example);
        if (userBaseList.isEmpty()) {
            throw new UsernameNotFoundException("user name invalid");
        }
        UserBase userBase = userBaseList.get(0);
        log.info("loadUserByUsername :: password :: " + userBase.getPassword());
        return new User(username, userBase.getPassword(), grantedAuthorities);
    }
}