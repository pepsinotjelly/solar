package com.bubble.service.impl;

import com.bubble.mapper.RatingRecordBMapper;
import com.bubble.model.*;
import com.bubble.service.RatingRecordService;
import com.bubble.thrift.BaseResp;
import com.bubble.thrift.recommend_service.GetRecommendInfoRequest;
import com.bubble.thrift.recommend_service.GetRecommendInfoResponse;
import com.bubble.utils.DataProcessor;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.SparseFieldMatrix;
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
        int N = request.getAListSize();
//        int M = request.getAListSize()/N;
        GetRecommendInfoResponse response = new GetRecommendInfoResponse();
        //  解析request获取En(A)、其他相关信息
        List<String> AList = request.getAList();
//        double[] EnA = new double[N];

        OpenMapRealMatrix AMatrix = new OpenMapRealMatrix(1, AList.size());
        for (int i = 0; i < N; i++) {
            AMatrix.setEntry(0, i,Double.parseDouble(AList.get(i)));
//            EnA[i] = Double.parseDouble(AList.get(i));
        }
        //  数据查询获取B
        RatingRecordBExample ratingRecordBExample = new RatingRecordBExample();
        ratingRecordBExample.createCriteria().andItemIdBetween(request.getStartPosition(), request.getEndPosition());
        List<RatingRecordB> ratingRecordBList = ratingRecordBMapper.selectByExample(ratingRecordBExample);
        // 初始化矩阵 B
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (RatingRecordB recordB : ratingRecordBList) {
            min = recordB.getUserId() < min ? recordB.getUserId() : min;
            max = recordB.getUserId() > max ? recordB.getUserId() : max;
        }
        int M = max - min + 1;
        OpenMapRealMatrix BMatrix = new OpenMapRealMatrix(M, N);
//        double[][] B = new double[ratingRecordBList.size()][N];// 溢出
        for (RatingRecordB recordB : ratingRecordBList) {
            BMatrix.addToEntry(recordB.getUserId() - min, recordB.getItemId() - request.getStartPosition(), recordB.getRating());
        }
        //  计算En(AB)，En(BB)
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        OpenMapRealMatrix ABMatrix = (OpenMapRealMatrix) AMatrix.multiply(BMatrix.transpose());
        OpenMapRealMatrix BBMatrix = (OpenMapRealMatrix) BMatrix.multiply(BMatrix.transpose());
//        double[][] AB = dataProcessor.getAMulB(EnA, B);
//        double[][] BB = dataProcessor.getAMulB(B, B);
        //  压缩矩阵
//        double[] AB_press = dataProcessor.getVectorCompression(AB);
//        double[] BB_press = dataProcessor.getVectorCompression(BB);
        //  构造返回值
        List<String> AB_result = new ArrayList<>();
        List<String> BB_result = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            AB_result.add(Double.toString(ABMatrix.getColumn(i)[0]));
        }
        for (int i = 0; i < M; i++) {
            double[] row = BBMatrix.getRow(i);
            for(int j = 0;j < M;j ++){
                BB_result.add(Double.toString(row[j]));
            }
        }
        response.setABList(AB_result);
        response.setBBList(BB_result);
        response.setM(M);
        response.setN(M);
        response.setBaseResp(new BaseResp().setStatusCode(0).setStatusMsg("done"));
        return response;
    }
}
