package com.bubble.utils.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/30 8:14 下午
 * @Desc :
 */
@Slf4j
@Component
public class MyAuthenticationFailHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        // 允许跨域
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        // 允许自定义请求头token(允许head跨域)
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "token, Accept, Origin, X-Requested-With, Content-Type, Last-Modified");
        log.info("MyAuthenticationFailHandler :: onAuthenticationFailure :: failure :: "+ e.getMessage());
        if(e instanceof UsernameNotFoundException){
            httpServletResponse.getWriter().write("can not found user :: ");
        }else if(e instanceof BadCredentialsException){
            httpServletResponse.getWriter().write("error password :: ");
        }
        httpServletResponse.setStatus(401);
        httpServletResponse.getWriter().write(e.getMessage());
    }
}