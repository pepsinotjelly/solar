package com.bubble.utils.filter;

import com.bubble.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/31 9:37 下午
 * @Desc : login Filter
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    // 会从 Spring Security 配置文件那里传过来
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        super(authenticationManager);
        log.info("JwtAuthorizationFilter :: set userDetailService");
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 判断是否有 token，并且进行认证
        Authentication token = getAuthentication(request);
        log.info("doFilterInternal :: get token");
        if (token == null) {
            chain.doFilter(request, response);
            log.info("doFilterInternal :: token is null!!!");
            return;
        }
        // 认证成功
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            log.info("getAuthentication :: no token!!");
            return null;
        }
        log.info("getAuthentication :: header :: "+header);
        String token = header.split(" ")[1];
        log.info("getAuthentication :: token :: "+token);
        String username = JwtUtil.getUsername(token);
        log.info("getAuthentication :: username :: "+username);
        UserDetails userDetails = null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
            log.info("getAuthentication :: loaduser");
        } catch (UsernameNotFoundException e) {
            return null;
        }
        if (!JwtUtil.verify(token, username, userDetails.getPassword())) {
            return null;
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}