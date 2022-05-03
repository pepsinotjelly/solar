package com.bubble.service.impl;

import com.bubble.mapper.RatingRecordBMapper;
import com.bubble.model.*;
import com.bubble.service.RatingRecordService;
import com.bubble.thrift.BaseResp;
import com.bubble.thrift.recommend_service.GetItemIdRequest;
import com.bubble.thrift.recommend_service.GetItemIdResponse;
import com.bubble.thrift.recommend_service.GetRecommendInfoRequest;
import com.bubble.thrift.recommend_service.GetRecommendInfoResponse;
import com.bubble.utils.CryptoSystem;
import com.bubble.utils.DataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.FieldElement;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.apache.commons.math3.linear.SparseFieldMatrix;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;
import paillierp.Paillier;
import paillierp.key.PaillierKey;

import javax.annotation.Resource;
import java.math.BigInteger;
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
        log.info("REQUEST RECEIVE :: GetRecommendInfoRequest");
        // 同步矩阵信息
        int N = request.getAListSize();
        GetRecommendInfoResponse response = new GetRecommendInfoResponse();
        //  解析request获取En(A)、其他相关信息
        List<String> AList = request.getAList();
        //  获取密钥
        Paillier eSystem = new Paillier();
        BigInteger n = new BigInteger(request.getPublicKeyN());
        long rnd = Long.parseLong(request.getPublicKeyRnd());
        PaillierKey publicKey = new PaillierKey(n, rnd);
        eSystem.setEncryption(publicKey);
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
        for (RatingRecordB recordB : ratingRecordBList) {
            BMatrix.addToEntry(recordB.getUserId() - min, recordB.getItemId() - request.getStartPosition(), recordB.getRating());
        }
        CryptoSystem cryptoSystem = new CryptoSystem();
        //  计算En(AB)
        BigInteger[] EnAB = new BigInteger[M];
        for (int i = 0; i < M; i++) {
            EnAB[i] = new BigInteger("1");
        }
        for (RatingRecordB recordB : ratingRecordBList) {
            int user_pos = recordB.getUserId() - min;
            int item_pos = recordB.getItemId() - request.getStartPosition();
            BigInteger A_item_pos = new BigInteger(AList.get(item_pos));
            BigInteger cons = new BigInteger(String.valueOf((int) (recordB.getRating() * 10)));
            if (!BigInteger.ZERO.equals(EnAB[user_pos])) {
                BigInteger EnAB_user_pos = new BigInteger(EnAB[user_pos].toString());
                EnAB[user_pos] = eSystem.multiply(A_item_pos, cons);
                EnAB[user_pos] = eSystem.add(EnAB[user_pos], EnAB_user_pos);
            } else {
                EnAB[user_pos] = eSystem.multiply(A_item_pos, cons);
            }
        }
        //  计算 BB
        OpenMapRealMatrix BBMatrix = (OpenMapRealMatrix) BMatrix.multiply(BMatrix.transpose());
        //  构造返回值
        List<String> AB_result = new ArrayList<>();
        List<String> BB_result = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            AB_result.add(EnAB[i].toString());
        }
        for (int i = 0; i < M; i++) {
            double[] row = BBMatrix.getRow(i);
            for (int j = 0; j < M; j++) {
                BB_result.add(Double.toString(Math.sqrt(row[j])));
            }
        }

        List<String> EnBB = cryptoSystem.Encryption(BB_result, 100, publicKey);
        response.setABList(AB_result);
        response.setBBList(EnBB);
        response.setM(M);
        response.setN(M);
        response.setBaseResp(new BaseResp().setStatusCode(0).setStatusMsg("done"));
        log.info("RESPONSE SENT :: GetRecommendInfoResponse");
        return response;
    }

    @Override
    public GetItemIdResponse GetItemId(GetItemIdRequest request) throws TException {
        log.info("GetRecommendItemIdRequest :: " + request.toString());
        GetItemIdResponse response = new GetItemIdResponse();
        //  解析请求-获取索引表、相似度
        List<String> EnCosineSimilarityList = request.getCosineSimilarityList();
        List<String> indexList = request.getIndexList();
        //  初始化加密工具
        Paillier eSystem = new Paillier();
        BigInteger n = new BigInteger(request.getPublicKeyN());
        long rnd = Long.parseLong(request.getPublicKeyRnd());
        PaillierKey publicKey = new PaillierKey(n, rnd);
        eSystem.setEncryption(publicKey);
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

        BigInteger[] ratingList = new BigInteger[N];
        for (int i = 0; i < N; i++) {
            ratingList[i] = new BigInteger("1");
        }
        for (RatingRecordB record : ratingRecordBList) {
            int user_pos = indexList.indexOf(record.getUserId().toString());
            int item_pos = itemList.indexOf(record.getItemId().toString());
            BigInteger B_user_pos = new BigInteger(EnCosineSimilarityList.get(user_pos));
            BigInteger cons = new BigInteger(String.valueOf((int) (record.getRating() * 10)));
            if (!BigInteger.ZERO.equals(ratingList[item_pos]) && ratingList[item_pos] != null && !BigInteger.ZERO.equals(cons)) {
                BigInteger RatingList_item_pos = new BigInteger(ratingList[item_pos].toString());
                ratingList[item_pos] = eSystem.multiply(B_user_pos, cons);
                ratingList[item_pos] = eSystem.add(ratingList[item_pos], RatingList_item_pos);
            } else {
                ratingList[item_pos] = eSystem.multiply(B_user_pos, cons);
            }
        }
        List<String> itemSim = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            itemSim.add(ratingList[i].toString());
        }
        response.setRatingList(itemSim);
        response.setItemIdList(itemList);
        response.setBaseResp(new BaseResp().setStatusMsg("DONE").setStatusCode(0));
//        log.info(String.valueOf(response));
        return response;
    }

    @Override
    public GetRecommendInfoResponse GetPlainRecommendInfo(GetRecommendInfoRequest request) throws TException {
        log.info("REQUEST RECEIVE :: GetRecommendInfoRequest");
        // 同步矩阵信息
        int N = request.getAListSize();
        GetRecommendInfoResponse response = new GetRecommendInfoResponse();
        //  解析request获取En(A)、其他相关信息
        List<String> AList = request.getAList();
        //  计算明文数据
        OpenMapRealMatrix AMatrix = new OpenMapRealMatrix(1, AList.size());
        for (int i = 0; i < N; i++) {
            AMatrix.setEntry(0, i, Double.parseDouble(AList.get(i)));
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
        for (RatingRecordB recordB : ratingRecordBList) {
            BMatrix.addToEntry(recordB.getUserId() - min, recordB.getItemId() - request.getStartPosition(), recordB.getRating());
        }
        //  计算 BB
        OpenMapRealMatrix ABMatrix = (OpenMapRealMatrix) AMatrix.multiply(BMatrix.transpose());
        OpenMapRealMatrix BBMatrix = (OpenMapRealMatrix) BMatrix.multiply(BMatrix.transpose());
        //  构造返回值
        List<String> AB_result = new ArrayList<>();
        List<String> BB_result = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            AB_result.add(Double.toString(ABMatrix.getColumn(i)[0]));
        }
        for (int i = 0; i < M; i++) {
            double[] row = BBMatrix.getRow(i);
            for (int j = 0; j < M; j++) {
                BB_result.add(Double.toString(Math.sqrt(row[j])));
            }
        }
        response.setABList(AB_result);
        response.setBBList(BB_result);
        response.setM(M);
        response.setN(M);
        response.setBaseResp(new BaseResp().setStatusCode(0).setStatusMsg("done"));
        log.info("RESPONSE SENT :: GetRecommendInfoResponse");
        return response;
    }

    @Override
    public GetItemIdResponse GetPlainRecommendItemId(GetItemIdRequest request) throws TException {
        log.info("GetPlainItemIdRequest :: " + request.toString());
        GetItemIdResponse response = new GetItemIdResponse();
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
            scoreMatrix.setEntry(indexList.indexOf(record.getUserId().toString()), itemList.indexOf(record.getItemId().toString()), record.getRating());
        }
        //  余弦相似度矩阵
        OpenMapRealMatrix simMatrix = new OpenMapRealMatrix(1, M);
        for (int i = 0; i < M; i++) {
            simMatrix.setEntry(0, i, Double.parseDouble(cosineSimilarityList.get(i)));
        }
        //  商品评估得分矩阵
        OpenMapRealMatrix itemSimMatrix = (OpenMapRealMatrix) simMatrix.multiply(scoreMatrix);
        List<String> itemSim = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            itemSim.add(Double.toString(itemSimMatrix.getEntry(0, i)));
        }
        response.setRatingList(itemSim);
        response.setItemIdList(itemList);
        response.setBaseResp(new BaseResp().setStatusMsg("DONE").setStatusCode(0));
        return response;
    }
}
