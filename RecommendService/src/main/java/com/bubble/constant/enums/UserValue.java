package com.bubble.constant.enums;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/12 6:25 PM
 * @Desc: 用户价值
 */
public class UserValue {

}
enum UserStatus{
    // 用户分群
    NEW_USER,        // v1 新用户 0-100
    PRIMARY_USER,    // v2 初级消费 100-1000
    REGULAR_USER,    // v3 老用户 1000-10000
    LOYAL_USER;      // v4 忠实用户 10000+
}
