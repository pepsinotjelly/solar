package com.bubble.utils.config;

import com.bubble.service.impl.MyCustomUserServiceImpl;
import com.bubble.utils.handler.MyAuthenticationFailHandler;
import com.bubble.utils.handler.MyAuthenticationSuccessHandler;
import com.bubble.utils.handler.MyLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/29 9:00 下午
 * @Desc : 适配器
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    UserDetailsService customUserService() {
        return new MyCustomUserServiceImpl();
    }

    /**
     * 身份验证接口
     */

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 使用自定义UserDetailsService
        auth.userDetailsService(customUserService()).passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 权限管理配置器 :: 授权
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().maximumSessions(1);
        http.formLogin()
                .loginProcessingUrl("/user/login").defaultSuccessUrl("/")
                //　自定义登录验证成功或失败后的去向
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenticationFailHandler)
                .and()
                .logout()
                .logoutUrl("/user/logout").logoutSuccessUrl("/")
                //  自动登出成功去向
                .logoutSuccessHandler(myLogoutSuccessHandler)
                // 禁用csrf防御机制(跨域请求伪造)
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                ;
    }

    /**
     * 强散列哈希加密实现 :: 加密
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
