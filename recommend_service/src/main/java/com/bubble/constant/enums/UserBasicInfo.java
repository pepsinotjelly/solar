package com.bubble.constant.enums;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/12 6:18 PM
 * @Desc: 用户基本信息
 */
public class UserBasicInfo {
}

enum Age{
    // 年龄段
    INFANTS_AND_YOUNG_CHILDREN_CONSUMER_GROUPS, // values-0: 0-6
    CHILDREN_CONSUMER_GROUPS,                   // values-1: 6-15
    YOUNG_CONSUMER_GROUPS,                      // values-2: 15-30
    MIDDLE_AGED_CONSUMER_GROUPS,                // values-3: 30-60
    ELDERLY_CONSUMER_GROUPS;                    // values-4: 60+
}

enum Contiment{
    // 洲际信息
    EUROPE,          // 欧洲
    ASIA,            // 亚洲
    NORTH_AMERICA,   // 北美
    SOUTH_AMERICA,   // 南美
    AFRICA,          // 非洲
    OCEANIA,         // 大洋洲
    ANTARCTICA;      // 南极洲?有人吗
}

enum GenderOf{
    // 性别
    MAN,        // 男性、默认性别
    WOMAN;      // 女性
}

enum Degree{
    // 学历
    DEFAULT_DEGREE,              // values-0: 默认学历
    JUNIOR_HIGH_SCHOOL_DEGREE,   // values-1: 初中
    HIGH_SCHOOL_DEGREE,          // values-2: 高中
    BACHELOR_DEGREE,             // values-3: 学士
    MASTERS_DEGREE,              // values-4: 硕士
    PHD_AND_ABOVE;               // values-5: 博士
}