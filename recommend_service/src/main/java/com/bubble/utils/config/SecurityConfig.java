//package com.bubble.utils.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
///**
// * @author : sunpengyu.sonia
// * @date : 2022/3/29 9:00 下午
// * @Desc :
// */
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and().csrf().disable();
//        super.configure(http);
//    }
//
//    /*
//     * 注入BCryptPasswordEncoder
//     */
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
