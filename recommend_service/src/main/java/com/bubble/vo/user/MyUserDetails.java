package com.bubble.vo.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/31 9:40 上午
 * @Desc :
 */
@Slf4j
public class MyUserDetails implements UserDetails {
    String username;
    String password;
    private Collection<? extends GrantedAuthority> authorities;

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


    public void setUsername(String username) {
        this.username = username;
        log.info("MyUserDetails :: setUsername :: "+username);
    }

    public void setPassword(String password) {
        this.password = password;
        log.info("MyUserDetails :: setPassword :: "+password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("MyUserDetails :: getAuthorities :: "+this.authorities);
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
