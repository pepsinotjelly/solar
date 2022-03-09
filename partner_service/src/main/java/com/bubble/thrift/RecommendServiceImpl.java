package com.bubble.thrift;

import com.bubble.thrift.recommend_service.*;
import org.apache.thrift.TException;

public class RecommendServiceImpl implements RecommendService.Iface {

    @Override
    public GetRecommendInfoResponse GetRecommendInfo(GetRecommendInfoRequest getRecommendInfoRequest) throws TException {
        // TODO
        //  解析request获取En(A)、其他相关信息
        //  数据查询获取B
        //  计算En(AB)，En(BB)
        //  压缩矩阵
        //  构造返回值

        return null;
    }

    @Override
    public SyncItemBaseResponse SyncItemBase(SyncItemBaseRequest syncItemBaseRequest) throws TException {
        // TODO
        //  读取信息
        //  对比数据库中信息
        //  写入数据库
        return null;
    }
}
