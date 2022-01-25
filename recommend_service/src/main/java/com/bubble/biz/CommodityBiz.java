package com.bubble.biz;

import com.bubble.dal.CommodityInfoService;
import com.bubble.thrift.recommend_service.GetCommodityInfoRequest;
import com.bubble.thrift.recommend_service.GetCommodityInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/25 8:06 PM
 * @Desc:
 */
public class CommodityBiz {
    @Autowired
    private CommodityInfoService commodityInfoService;
    public GetCommodityInfoResponse GetCommodityInfo(GetCommodityInfoRequest request){
        return null;
    }
}
