package com.bubble.service.impl;

import com.bubble.mapper.ItemBaseMapper;
import com.bubble.mapper.RatingRecordMapper;
import com.bubble.model.*;
import com.bubble.service.UserRecommendService;
import com.bubble.mapper.UserRecommendMapper;
import com.bubble.thrift.ThriftProxyFactory;
import com.bubble.thrift.recommend_service.*;
import com.bubble.utils.DataProcessor;
import com.bubble.utils.IdWorker;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserRecommendServiceImpl implements UserRecommendService {
    @Resource
    UserRecommendMapper userRecommendMapper;
    @Resource
    RatingRecordMapper ratingRecordMapper;

    @Resource
    private ItemBaseMapper itemBaseMapper;

    private final String[] hostPorts = new String[]{"localhost:7090"};
    private RecommendService.Iface client = (RecommendService.Iface) ThriftProxyFactory.newInstance(RecommendService.class, hostPorts);


    @Override
    public List<String> getSimilarityList(List<Integer> userIdList) throws Exception {
        List<String> cosineSimilarityList = new ArrayList<>();
        // TODO
        //  构造A矩阵
        RatingRecordExample example = new RatingRecordExample();
        example.createCriteria().andUserIdEqualTo(userIdList.get(0));
        List<RatingRecord> recordList = ratingRecordMapper.selectByExample(example);
        // 计算item_list边界
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (RatingRecord record : recordList) {
            max = record.getItemId() > max ? record.getItemId() : max;
            min = record.getItemId() < min ? record.getItemId() : min;
        }
        int N_item = max - min + 1; // item元素的个数
        int M_item = 1;
        // 初始化矩阵A
        OpenMapRealMatrix AMatrix = new OpenMapRealMatrix(1, N_item);
        for (RatingRecord record : recordList) {
            AMatrix.setEntry(0, record.getItemId() % N_item, record.getRating());
        }
        //  计算AA
        OpenMapRealMatrix AAMatrix = new OpenMapRealMatrix(1, 1);
        AAMatrix = (OpenMapRealMatrix) AMatrix.multiply(AMatrix.transpose());
        // 明文基础上进行数据掩盖
        //  加密
        //  密文基础上去除掩盖
        //  数据转换
        List<String> AList = new ArrayList<>();
        for (double a : AMatrix.getRow(0)) {
            AList.add(Double.toString(a));
        }
        //  发送请求
        GetRecommendInfoRequest request = new GetRecommendInfoRequest();
        request.setAList(AList);
        request.setEndPosition(max);
        request.setStartPosition(min);
        log.info("start requesting");
        GetRecommendInfoResponse response = client.GetRecommendInfo(request);
        log.info("GetRecommendInfoResponse :: "+ response);
        //获取AB、BB
        List<String> EnAB = response.getABList();
        List<String> EnBB = response.getBBList();
        //  密文基础上进行数据掩盖
        //TODO 调用密文加减法计算函数
        //  解密AB、BB
//        List<String> DeAB = new ArrayList<>();
//        List<String> DeBB = new ArrayList<>();
        //  明文基础上去除数据掩盖
        //TODO 调用密文加减法计算函数

        //  数据类型转换
        int N_user = response.getN();
        int M_user = response.getM();
        //  初始化BB、AB矩阵
        OpenMapRealMatrix BBMatrix = new OpenMapRealMatrix(M_user, N_user);
        OpenMapRealMatrix ABMatrix = new OpenMapRealMatrix(M_user, 1);
        for (int i = 0; i < M_user; i++) {
            for (int j = 0; j < N_user; j++) {
                BBMatrix.setEntry(i, j, Double.parseDouble(EnBB.get(i * N_user + j)));
            }
        }
        for (int i = 0; i < N_user; i++) {
            ABMatrix.setEntry(i, 0, Double.parseDouble(EnAB.get(i)));
        }
        // 计算余弦相似度
        OpenMapRealMatrix cosineMatrix = new OpenMapRealMatrix(1, M_user);
        for (int i = 0; i < M_user; i++) {
            double molecular = ABMatrix.getEntry(i, 0);
            OpenMapRealMatrix BB_path = new OpenMapRealMatrix(1, M_user);
            for (int j = 0; j < M_user; j++) {
                BB_path.setEntry(0, j, BBMatrix.getEntry(i, j));
            }
            double denominator = AAMatrix.multiply(BB_path).getEntry(0, 0);
            if (Double.compare(denominator, 0.0) > 0 && Double.compare(molecular, 0.0) > 0) {
                cosineMatrix.setEntry(0, i, (molecular / denominator));
                cosineSimilarityList.add(Double.toString(cosineMatrix.getEntry(0, i)));
            }
        }
        log.info("cosineMatrix :: " + cosineMatrix.toString());
        List<String> index_list = response.getIndexList();

        //  获取排序结果
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        int[] topList = dataProcessor.Arraysort(cosineMatrix.getColumn(0),true);
        // 获取 top 20 Index
        List<String> finalIndex = new ArrayList<>();
        List<String> partCosineSimilarity = new ArrayList<>();
        for(int i = 0;i < 20;i ++){
            finalIndex.add(index_list.get(topList[i]));
            partCosineSimilarity.add(Double.toString(cosineMatrix.getColumn(0)[topList[i]]));
        }
        //  获取Item列表
//        List<Integer> itemIdList = getRecommendList(partCosineSimilarity,finalIndex);
        //  获取对应itemInfo
        //  构造返回值
        List<String> demo = new ArrayList<>();
//        for(Integer s: itemIdList){
//            demo.add(s.toString());
//        }
        return demo;
    }

    @Override
    public List<Integer> getRecommendList(List<String> cosineSimilarity, List<String> Index) throws Exception {
        List<Integer> recommendIdList = new ArrayList<>();
        // 请求接口获取item ID list
        // 加密余弦相似度
        List<String> EnCS = new ArrayList<>();
        GetRecommendItemIdRequest request = new GetRecommendItemIdRequest();
        request.setIndexList(Index);
        request.setCosineSimilarityList(cosineSimilarity);
        GetRecommendItemIdResponse response= client.GetRecommendItemId(request);
        List<String> itemIdList = response.getItemIdList();
        List<String> ratingList = response.getRatingList();
        //  解密ratingList
        //  格式化
        double[] itemSimList = new double[ratingList.size()];
        for(int i = 0;i < ratingList.size();i ++){
            itemSimList[i] = Double.parseDouble(ratingList.get(i));
        }
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        int[] itemIndex = dataProcessor.Arraysort(itemSimList,true);
        for(int i = 0;i < 12;i ++){
            recommendIdList.add(Integer.parseInt(itemIdList.get(itemIndex[i])));
        }
        return recommendIdList;
    }


//    public List<ItemBase> getPlainCosineSimilarityList(List<Integer> userIdList) throws Exception {
//        List<ItemBase> recommendList = new ArrayList<>();
//        // TODO
//        //  构造A矩阵
//        RatingRecordExample example = new RatingRecordExample();
//        example.createCriteria().andUserIdEqualTo(userIdList.get(0));
//        List<RatingRecord> recordList = ratingRecordMapper.selectByExample(example);
//        // 计算item_list边界
//        int max = 0;
//        int min = Integer.MAX_VALUE;
//        for (RatingRecord record : recordList) {
//            max = record.getItemId() > max ? record.getItemId() : max;
//            min = record.getItemId() < min ? record.getItemId() : min;
//        }
//        int N_item = max - min + 1; // item元素的个数
//        int M_item = 1;
//        // 初始化矩阵A
//        OpenMapRealMatrix AMatrix = new OpenMapRealMatrix(1, N_item);
//        for (RatingRecord record : recordList) {
//            AMatrix.setEntry(0, record.getItemId() % N_item, record.getRating());
//        }
//        //  计算AA
//        OpenMapRealMatrix AAMatrix = new OpenMapRealMatrix(1, 1);
//        AAMatrix = (OpenMapRealMatrix) AMatrix.multiply(AMatrix.transpose());
//        //  数据转换
//        List<String> AList = new ArrayList<>();
//        for (double a : AMatrix.getRow(0)) {
//            AList.add(Double.toString(a));
//        }
//        //  发送请求
//        GetRecommendInfoRequest request = new GetRecommendInfoRequest();
//        request.setAList(AList);
//        request.setEndPosition(max);
//        request.setStartPosition(min);
//        GetRecommendInfoResponse response = client.GetRecommendInfo(request);
//        //获取AB、BB
//        List<String> EnAB = response.getABList();
//        List<String> EnBB = response.getBBList();
//        log.info("EnAB :: " + EnAB.toString());
//        log.info("EnBB :: " + EnBB.toString());
//        //  数据类型转换
//        int N_user = response.getN();
//        int M_user = response.getM();
//        //  初始化BB、AB矩阵
//        OpenMapRealMatrix BBMatrix = new OpenMapRealMatrix(M_user, N_user);
//        OpenMapRealMatrix ABMatrix = new OpenMapRealMatrix(M_user, 1);
//        for (int i = 0; i < M_user; i++) {
//            for (int j = 0; j < N_user; j++) {
//                BBMatrix.setEntry(i, j, Double.parseDouble(EnBB.get(i * N_user + j)));
//            }
//        }
//        for (int i = 0; i < N_user; i++) {
//            ABMatrix.setEntry(i, 0, Double.parseDouble(EnAB.get(i)));
//        }
//        // 计算余弦相似度
//        OpenMapRealMatrix cosineMatrix = new OpenMapRealMatrix(1, M_user);
//        for (int i = 0; i < M_user; i++) {
//            double molecular = ABMatrix.getEntry(i, 0);
//            OpenMapRealMatrix BB_path = new OpenMapRealMatrix(1, M_user);
//            for (int j = 0; j < M_user; j++) {
//                BB_path.setEntry(0, j, BBMatrix.getEntry(i, j));
//            }
//            double denominator = AAMatrix.multiply(BB_path).getEntry(0, 0);
//            if (Double.compare(denominator, 0.0) > 0 && Double.compare(molecular, 0.0) > 0) {
//                cosineMatrix.setEntry(0, i, (molecular / denominator));
//            }
//        }
//        log.info("cosineMatrix :: " + cosineMatrix.toString());
//        //  获取排序结果
//        //  获取Item列表
//        List<ItemBase> itemBaseList = null;
//        //  获取对应itemInfo
//        //  构造返回值
//        return recommendList;
//    }

    @Override
    public List<UserRecommend> getUserRecommendList(int userId) {
        UserRecommendExample userRecommendExample = new UserRecommendExample();
        userRecommendExample.createCriteria().andIdEqualTo(userId);
        return userRecommendMapper.selectByExample(userRecommendExample);
    }

    @Override
    public int updateUserRecommend(int userId, List<UserRecommend> userRecommendList) {
        UserRecommendExample userRecommendExample = new UserRecommendExample();
        userRecommendExample.createCriteria().andUserIdEqualTo(userId);
        userRecommendMapper.deleteByExample(userRecommendExample);
        for (UserRecommend userRecommend : userRecommendList) {
            IdWorker worker = IdWorker.getIdWorker();
            userRecommend.setId(1);
            userRecommendMapper.insert(userRecommend);
        }
        return 0;
    }
}
