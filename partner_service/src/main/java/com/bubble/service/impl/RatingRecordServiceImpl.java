package com.bubble.service.impl;

import com.bubble.mapper.RatingRecordBMapper;
import com.bubble.model.*;
import com.bubble.service.RatingRecordService;
import com.bubble.thrift.BaseResp;
import com.bubble.thrift.recommend_service.GetRecommendInfoRequest;
import com.bubble.thrift.recommend_service.GetRecommendInfoResponse;
import com.bubble.utils.DataProcessor;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class RatingRecordServiceImpl implements RatingRecordService {
    @Resource
    private RatingRecordBMapper ratingRecordBMapper;

    @Override
    public GetRecommendInfoResponse GetRecommendInfo(GetRecommendInfoRequest request) throws TException {
        // TODO MN信息的同步
        int M = 10;
        int N = 100;
        GetRecommendInfoResponse response = new GetRecommendInfoResponse();
        //  解析request获取En(A)、其他相关信息
        List<String> AList = request.getAList();
        Double[][] EnA = new Double[M][N];
        int i = 0;
        for (String code : AList) {
            EnA[i/M][i%M] = Double.parseDouble(code);
            i++;
        }
        //  数据查询获取B
        RatingRecordBExample ratingRecordBExample = new RatingRecordBExample();
        ratingRecordBExample.createCriteria().andItemIdBetween(request.getStartPosition(), request.getEndPosition());
        List<RatingRecordB> ratingRecordBList = ratingRecordBMapper.selectByExample(ratingRecordBExample);
        // 初始化矩阵 B
        Double[][] B = new Double[ratingRecordBList.size()][N];
        for(RatingRecordB recordB : ratingRecordBList){
            B[recordB.getUserId()][recordB.getItemId() - request.getStartPosition()] = recordB.getRating();
        }
        //  计算En(AB)，En(BB)
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        Double[][] AB = dataProcessor.getAMulB(EnA,B);
        Double[][] BB = dataProcessor.getAMulB(B,B);
        //  压缩矩阵
        Double[] AB_press = dataProcessor.getVectorCompression(AB);
        Double[] BB_press = dataProcessor.getVectorCompression(BB);
        //  构造返回值
        List<String> AB_result = new ArrayList<>();
        List<String> BB_result = new ArrayList<>();
        for(i = 0;i < AB_press.length;i ++){
            AB_result.add(AB_press[i].toString());
        }
        for(i = 0;i < BB_press.length;i ++){
            BB_result.add(BB_press[i].toString());
        }
        response.setABList(AB_result);
        response.setBBList(BB_result);
        response.setBaseResp(new BaseResp().setStatusCode(0).setStatusMsg("done"));
        return response;
    }
}
