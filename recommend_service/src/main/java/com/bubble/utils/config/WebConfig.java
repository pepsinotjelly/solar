package com.bubble.utils.config;

import com.bubble.utils.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/20 2:58 PM
 * @Desc: 启动interceptor 完成用户鉴权
 */
@Configuration
//注入bean
public class WebConfig implements WebMvcConfigurer {
     @Override
     public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(new LogInterceptor());
     }
}
