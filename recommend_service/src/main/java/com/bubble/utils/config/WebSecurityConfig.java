package com.bubble.utils.config;

import com.bubble.service.impl.UserDetailsServiceImpl;
import com.bubble.utils.filter.JwtAuthenticationFilter;
import com.bubble.utils.filter.JwtAuthorizationFilter;
import com.bubble.utils.handler.MyAuthenticationFailHandler;
import com.bubble.utils.handler.MyAuthenticationSuccessHandler;
import com.bubble.utils.handler.MyLogoutSuccessHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/29 9:00 下午
 * @Desc : 适配器
 */

@Configuration
@EnableWebSecurity
// 开启注解配置支持
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    // Spring Boot 的 CacheManager，这里我们使用 JCache
    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;


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
                .csrf().disable();
        // 开启跨域
        http.cors()
                .and()
                // security 默认 csrf 是开启的，我们使用了 token ，这个也没有什么必要了
                .csrf().disable()
                .authorizeRequests()
                // 默认所有请求通过，但是我们要在需要权限的方法加上安全注解，这样比写死配置灵活很多
                .anyRequest().permitAll()
                .and()
                // 添加自己编写的两个过滤器
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), cachingUserDetailsService(userDetailsServiceImpl)))
                // 前后端分离是 STATELESS，故 session 使用该策略
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * 强散列哈希加密实现 :: 加密
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * 身份验证接口
     */

    // 此处配置 AuthenticationManager，并且实现缓存
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 对自己编写的 UserDetailsServiceImpl 进一步包装，实现缓存
        CachingUserDetailsService cachingUserDetailsService = cachingUserDetailsService(userDetailsServiceImpl);
        // jwt-cache 我们在 ehcache.xml 配置文件中有声明
        UserCache userCache = new SpringCacheBasedUserCache(cacheManager.getCache("jwt-cache"));
        cachingUserDetailsService.setUserCache(userCache);
        /*
        security 默认鉴权完成后会把密码抹除，但是这里我们使用用户的密码来作为 JWT 的生成密钥，
        如果被抹除了，在对 JWT 进行签名的时候就拿不到用户密码了，故此处关闭了自动抹除密码。
         */
        auth.eraseCredentials(false);
        auth.userDetailsService(cachingUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    /*
    此处我们实现缓存的时候，我们使用了官方现成的 CachingUserDetailsService ，但是这个类的构造方法不是 public 的，
    我们不能够正常实例化，所以在这里进行曲线救国。
     */
    private CachingUserDetailsService cachingUserDetailsService(UserDetailsServiceImpl delegate) {

        Constructor<CachingUserDetailsService> ctor = null;
        try {
            ctor = CachingUserDetailsService.class.getDeclaredConstructor(UserDetailsService.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Assert.notNull(ctor, "CachingUserDetailsService constructor is null");
        ctor.setAccessible(true);
        return BeanUtils.instantiateClass(ctor, delegate);
    }
}
