package com.bubble.utils.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.bubble.constant.annotations.PassToken;
import com.bubble.constant.annotations.UserLoginToken;
import com.bubble.service.UserService;
import com.bubble.vo.BaseUser;
import com.bubble.vo.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/20 2:48 PM
 * @Desc:
 */
@Slf4j
public class AuthenticationInterceptor extends LogInterceptor{
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // TODO check authority
        String token = request.getHeader("token");
        //  不需要鉴权的方法直接通过
        if(! (handler instanceof HandlerMethod) ){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = (Method) handlerMethod.getMethod();
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
        }
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 user id
                String userId;
                try {
                    userId = JWT.decode(token).getAudience().get(0);
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                BaseUser user = userService.findUserById(userId);
                if (user == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getUserPwd())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("401");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO pass
        super.postHandle(request, response, handler, modelAndView);
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //TODO time
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
