package com.bubble.thrift;

import com.bubble.mapper.ItemBaseBMapper;
import com.bubble.service.ItemBaseBService;
import com.bubble.service.RatingRecordService;
import com.bubble.thrift.recommend_service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Component
public class RecommendServiceImpl implements RecommendService.Iface {
    @Autowired
    private ItemBaseBService itemBaseBService;
    @Autowired
    private RatingRecordService ratingRecordService;

    @Override
    public GetRecommendInfoResponse GetRecommendInfo(GetRecommendInfoRequest request) throws TException {
        return ratingRecordService.GetRecommendInfo(request);
    }

    @Override
    public SyncItemBaseResponse SyncItemBase(SyncItemBaseRequest syncItemBaseRequest) throws TException {
        return itemBaseBService.SyncItemBase(syncItemBaseRequest);
    }

    @Override
    public GetItemIdResponse GetItemId(GetItemIdRequest getItemIdRequest) throws TException {
        return null;
    }
}
