package com.bubble.thrift;

import com.bubble.mapper.ItemBaseBMapper;
import com.bubble.service.ItemBaseBService;
import com.bubble.thrift.recommend_service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Component
public class RecommendServiceImpl implements RecommendService.Iface {
    @Autowired
    private ItemBaseBService itemBaseBService;

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
        //  读取信息
        SyncItemBaseResponse response = new SyncItemBaseResponse();
        boolean flag = itemBaseBService.SyncItemBase(syncItemBaseRequest);
        // 构造返回值
        response.setDone(flag);
        BaseResp baseResp = new BaseResp();
        baseResp.setStatusCode(0);
        response.setBaseResp(baseResp);
        return response;
    }
}
