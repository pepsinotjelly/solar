package com.bubble.service.impl;

import com.bubble.mapper.RatingRecordBMapper;
import com.bubble.model.*;
import com.bubble.service.RatingRecordService;
import com.bubble.thrift.BaseResp;
import com.bubble.thrift.recommend_service.GetRecommendInfoRequest;
import com.bubble.thrift.recommend_service.GetRecommendInfoResponse;
import com.bubble.thrift.recommend_service.GetRecommendItemIdRequest;
import com.bubble.thrift.recommend_service.GetRecommendItemIdResponse;
import com.bubble.utils.DataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.SparseFieldMatrix;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class RatingRecordServiceImpl implements RatingRecordService {
    @Resource
    private RatingRecordBMapper ratingRecordBMapper;

    @Override
    public GetRecommendInfoResponse GetRecommendInfo(GetRecommendInfoRequest request) throws TException {
        log.info("GetRecommendInfoRequest :: "+request.toString());
        // 同步矩阵信息
        int N = request.getAListSize();
        GetRecommendInfoResponse response = new GetRecommendInfoResponse();
        //  解析request获取En(A)、其他相关信息
        List<String> AList = request.getAList();
        OpenMapRealMatrix AMatrix = new OpenMapRealMatrix(1, AList.size());
        for (int i = 0; i < N; i++) {
            AMatrix.setEntry(0, i, Double.parseDouble(AList.get(i)));
        }
        log.info("数据查询获取B");
        //  数据查询获取B
        RatingRecordBExample ratingRecordBExample = new RatingRecordBExample();
        ratingRecordBExample.createCriteria().andItemIdBetween(request.getStartPosition(), request.getEndPosition());
        List<RatingRecordB> ratingRecordBList = ratingRecordBMapper.selectByExample(ratingRecordBExample);
        // 初始化矩阵 B
        log.info("初始化矩阵 B");
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (RatingRecordB recordB : ratingRecordBList) {
            min = recordB.getUserId() < min ? recordB.getUserId() : min;
            max = recordB.getUserId() > max ? recordB.getUserId() : max;
        }
        int M = max - min + 1;
        String[] user_list = new String[M];
        List<String> userIndex = new ArrayList<>();
        OpenMapRealMatrix BMatrix = new OpenMapRealMatrix(M, N);
        log.info("OpenMapRealMatrix BMatrix");
        for (RatingRecordB recordB : ratingRecordBList) {
            BMatrix.addToEntry(recordB.getUserId() - min, recordB.getItemId() - request.getStartPosition(), recordB.getRating());
            user_list[recordB.getUserId() - min] = recordB.getUserId().toString();
        }
        userIndex.addAll(Arrays.asList(user_list));
        //  计算En(AB)，En(BB)
        log.info("计算En(AB)，En(BB)");
        OpenMapRealMatrix ABMatrix = (OpenMapRealMatrix) AMatrix.multiply(BMatrix.transpose());
        OpenMapRealMatrix BBMatrix = (OpenMapRealMatrix) BMatrix.multiply(BMatrix.transpose());
        //  构造返回值
        log.info("构造返回值");
        List<String> AB_result = new ArrayList<>();
        List<String> BB_result = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            AB_result.add(Double.toString(ABMatrix.getColumn(i)[0]));
        }
        for (int i = 0; i < M; i++) {
            double[] row = BBMatrix.getRow(i);
            for (int j = 0; j < M; j++) {
                BB_result.add(Double.toString(row[j]));
            }
        }
        log.info("response");
        response.setABList(AB_result);
        response.setBBList(BB_result);
        response.setIndexList(userIndex);
        response.setM(M);
        response.setN(M);
        response.setBaseResp(new BaseResp().setStatusCode(0).setStatusMsg("done"));
        log.info("set base resp");
        return response;
    }

    @Override
    public GetRecommendItemIdResponse GetRecommendItemId(GetRecommendItemIdRequest request) throws TException {
        log.info("GetRecommendItemIdRequest :: "+request.toString());
        GetRecommendItemIdResponse response = new GetRecommendItemIdResponse();
        //  解析请求-获取索引表、相似度
        List<String> cosineSimilarityList = request.getCosineSimilarityList();
        List<String> indexList = request.getIndexList();
        //  类型转换
        List<Integer> userIndex = new ArrayList<>();
        for (String s : indexList) {
            userIndex.add(Integer.parseInt(s));
        }
        RatingRecordBExample example = new RatingRecordBExample();
        example.createCriteria().andUserIdIn(userIndex);
        List<RatingRecordB> ratingRecordBList = ratingRecordBMapper.selectByExample(example);
        //  获取边界值
        List<String> itemList = new ArrayList<>();
        for (RatingRecordB record : ratingRecordBList) {
            if (!itemList.contains(record.getItemId().toString())) {
                itemList.add(record.getItemId().toString());
            }
        }
        int M = indexList.size(); //  用户数量
        int N = itemList.size();  //  item数量
        OpenMapRealMatrix scoreMatrix = new OpenMapRealMatrix(M, N);
        for (RatingRecordB record : ratingRecordBList) {
            scoreMatrix.setEntry(itemList.indexOf(record.getItemId().toString()), indexList.indexOf(record.getUserId().toString()), record.getRating());
        }
        //  余弦相似度矩阵
        OpenMapRealMatrix simMatrix = new OpenMapRealMatrix(1, M);
        for(int i = 0;i < M;i ++){
            simMatrix.setEntry(0,i,Integer.parseInt(cosineSimilarityList.get(i)));
        }
        //  商品评估得分矩阵
        OpenMapRealMatrix itemSimMatrix = (OpenMapRealMatrix) simMatrix.multiply(scoreMatrix.transpose());
        List<String> itemSim = new ArrayList<>();
        for(int i = 0;i < N;i ++){
            itemSim.add(Double.toString(itemSimMatrix.getEntry(0,i)));
        }
        response.setRatingList(itemSim);
        response.setItemIdList(itemList);
        response.setBaseResp(new BaseResp().setStatusMsg("DONE").setStatusCode(0));
        return response;
    }
}
