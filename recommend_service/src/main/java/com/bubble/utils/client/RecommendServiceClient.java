package com.bubble.utils.client;

import com.bubble.thrift.recommend_service.*;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

@Component
public class RecommendServiceClient {
    String[] hostPorts = new String[]{"localhost:7090"};
    RecommendService.Iface client = (RecommendService.Iface) ThriftProxyFactory.newInstance(RecommendService.class, hostPorts);

    public boolean SyncItemBase() throws TException {
        SyncItemBaseRequest request = new SyncItemBaseRequest();
        // TODO
        //  读取数据
        //  拼接数据
        SyncItemBaseResponse syncItemBaseResponse = client.SyncItemBase(request);
        return syncItemBaseResponse.done;
    }

    public String GetRecommendInfo(GetRecommendInfoRequest request) throws TException {
        GetRecommendInfoResponse getRecommendInfoResponse = client.GetRecommendInfo(request);
        // TODO
        //  初始化矩阵 A
        //  解析矩阵AB
        //  解析矩阵BB
        //  计算余弦相似度
        return "done";
    }
}