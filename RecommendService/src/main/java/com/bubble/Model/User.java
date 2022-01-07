package com.bubble.Model;

import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/6 9:43 PM
 * @Desc: 定义用户画像内容
 */
public class User {
    private String UserName;
    private String UserId;
    // 用户基本信息
        // 人口属性-性别/年龄区间/教育信息
    private int Gender;
    private int Age;
    private int Degree;
        // 位置信息-洲
    private int Continent;
    // 用户关联信息
        // 试用关系
        // 订阅关系-订阅方式-订阅状态 follow_status
        // 社交关系-好友 user_relationship
    // 用户价值
        // 消费能力区间
    private int ConsumptionCapacity;
        // 用户分群-新用户/老用户
    private int UserStatus;
    // 用户兴趣爱好
    private int hobby;
        // 阅读习惯-平板/PC/手机
    private int ReadingHabit;
        //内容偏好-美妆/服饰/生活内容 user_consumption_records 用户消费记录
        //美妆偏好-品牌/产地/类别 user_consumption_record
    // 需求特征
        //当前需求-近期浏览内容/商品/试用 -user_trail_record
    // 数据版本信息
    private String CreateTime;
    private String ModifyTime;


}
