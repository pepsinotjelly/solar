package com.bubble.service.impl;

import com.bubble.mapper.ItemBaseMapper;
import com.bubble.mapper.ItemInfoMapper;
import com.bubble.mapper.RatingRecordMapper;
import com.bubble.model.*;
import com.bubble.service.RecommendService;
import com.bubble.mapper.UserRecommendMapper;
import com.bubble.thrift.ThriftProxyFactory;
import com.bubble.thrift.recommend_service.*;
import com.bubble.utils.CryptoSystem;
import com.bubble.utils.DataProcessor;
import com.bubble.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.springframework.stereotype.Service;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RecommendServiceImpl implements RecommendService {
    @Resource
    private UserRecommendMapper userRecommendMapper;
    @Resource
    private RatingRecordMapper ratingRecordMapper;
    @Resource
    private ItemInfoMapper itemInfoMapper;
    @Resource
    private ItemBaseMapper itemBaseMapper;

    private int[] DEFAULT_ITEM_LIST = new int[]{53, 99, 148, 430, 467, 496, 549, 626, 633, 876, 1140, 1150, 1151, 1406, 1533, 1571, 1631, 2075, 2196, 2314, 2824, 2969, 3046, 3073, 3086, 3096, 3241, 3302, 3473, 3496, 3567, 3678};
    private final String[] hostPorts = new String[]{"localhost:7090"};
    private com.bubble.thrift.recommend_service.RecommendService.Iface client = (com.bubble.thrift.recommend_service.RecommendService.Iface) ThriftProxyFactory.newInstance(com.bubble.thrift.recommend_service.RecommendService.class, hostPorts);


    @Override
    public List<String> getSimilarityList(int userId) throws Exception {
        //  构造A矩阵
        RatingRecordExample ratingRecordExample = new RatingRecordExample();
        ratingRecordExample.createCriteria().andUserIdEqualTo(userId);
        List<RatingRecord> userWatchedRecordList = ratingRecordMapper.selectByExample(ratingRecordExample);
        // 计算item_list边界
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (RatingRecord record : userWatchedRecordList) {
            max = record.getItemId() > max ? record.getItemId() : max;
            min = record.getItemId() < min ? record.getItemId() : min;
        }
        int N_item = max - min + 1; // item元素的个数
        int M_item = 1;
        // 初始化矩阵A
        OpenMapRealMatrix AMatrix = new OpenMapRealMatrix(1, N_item);
        for (RatingRecord record : userWatchedRecordList) {
            AMatrix.setEntry(0, record.getItemId() % N_item, record.getRating());
        }
        //  数据转换
        List<String> AList = new ArrayList<>();
        for (double a : AMatrix.getRow(0)) {
            AList.add(Double.toString(a));
        }//  TODO 加密
        CryptoSystem cryptoSystem = new CryptoSystem();
        PaillierPrivateKey privateKey = cryptoSystem.getPrivateKey();
        PaillierKey publicKey = privateKey.getPublicKey();
        List<String> EnA = cryptoSystem.Encryption(AList, 100, publicKey);

        //  发送请求
        GetRecommendInfoRequest request = new GetRecommendInfoRequest();
        request.setAList(EnA);
        request.setEndPosition(max);
        request.setStartPosition(min);
        request.setPublicKeyN(publicKey.getN().toString());
        request.setPublicKeyRnd("122333356");
//        log.info(String.valueOf(request));
        GetRecommendInfoResponse response = client.GetRecommendInfo(request);
        //获取AB、BB
        List<String> EnAB = response.getABList();
//        log.info("EnAB :: " + EnAB.toString());
        List<String> EnBB = response.getBBList();
//        log.info("EnBB :: " + EnBB.toString());
        //  解密AB、BB
        List<String> DeAB = cryptoSystem.Decryption(EnAB, 100, privateKey);
//        log.info("DeAB :: " + DeAB);
        List<String> DeBB = cryptoSystem.Decryption(EnBB, 100, privateKey);
//        log.info("DeBB :: " + DeBB);
        //  数据类型转换
        int N_user = response.getN();
        int M_user = response.getM();
        //  计算AA
        OpenMapRealMatrix cosineMatrix = getCosineSimilarity(M_user, N_user, N_item
                , userWatchedRecordList, AMatrix, DeBB, DeAB);
        //  对应B用户索引列表
        List<String> index_list = new ArrayList<>();
        for (int i = 0; i < 600; i++) {
            index_list.add(Integer.toString(i + 1));
        }
        //  获取排序结果（索引）
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        int[] topList = dataProcessor.Arraysort(cosineMatrix.getRow(0), true);
        // 获取 top 20 Index
        List<String> finalIndex = new ArrayList<>();
        List<String> partCosineSimilarity = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            finalIndex.add(index_list.get(topList[i]));
            partCosineSimilarity.add(Double.toString(cosineMatrix.getRow(0)[topList[i]]));
        }
        //  获取Item列表
        List<Integer> itemIdList = getRecommendList(partCosineSimilarity, finalIndex);
        //  过滤数据
        log.info("START FILTERING");
        Boolean flag = updateRecommendList(itemIdList, userWatchedRecordList
                , userId, cosineMatrix, topList);
        //  构造返回值
        List<String> demo = new ArrayList<>();
        demo.add("TASK :: ");
        if (flag) demo.add("DONE!!!");
        return demo;
    }

    @Override
    public List<Integer> getRecommendList(List<String> cosineSimilarity, List<String> Index) throws Exception {
        List<Integer> recommendIdList = new ArrayList<>();
        //  加密余弦相似度
        CryptoSystem cryptoSystem = new CryptoSystem();
        PaillierPrivateKey privateKey = cryptoSystem.getPrivateKey();
        PaillierKey publicKey = privateKey.getPublicKey();
        List<String> EnCS = cryptoSystem.Encryption(cosineSimilarity, 100000000, privateKey);
        //  构造请求
        GetItemIdRequest request = new GetItemIdRequest();
        request.setIndexList(Index);
        request.setCosineSimilarityList(EnCS);
        request.setPublicKeyN(publicKey.getN().toString());
        request.setPublicKeyRnd("122333356");
        GetItemIdResponse response = client.GetItemId(request);
        //  获取商品推荐数据itemIdList、预计评平分数据ratingList
        List<String> itemIdList = response.getItemIdList();
        List<String> ratingList = response.getRatingList();
        //  解密ratingList
        List<String> DeRatingList = cryptoSystem.Decryption(ratingList, 1000000000, privateKey);
        //  格式化
        double[] itemSimList = new double[DeRatingList.size()];
        for (int i = 0; i < DeRatingList.size(); i++) {
            itemSimList[i] = Double.parseDouble(DeRatingList.get(i));
        }
        //  排序评分结果
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        int[] itemIndex = dataProcessor.Arraysort(itemSimList, true);
        //  构造返回值
        for (int i = 0; i < Math.min(itemIndex.length, 300); i++) {
            recommendIdList.add(Integer.parseInt(itemIdList.get(itemIndex[i])));
        }
//        log.info("recommendIdList :: " + recommendIdList);
        return recommendIdList;
    }

    @Override
    public List<String> getPlainSimilarityList(int userId) throws Exception {
        //  构造A矩阵
        RatingRecordExample ratingRecordExample = new RatingRecordExample();
        ratingRecordExample.createCriteria().andUserIdEqualTo(userId);
        List<RatingRecord> userWatchedRecordList = ratingRecordMapper.selectByExample(ratingRecordExample);
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (RatingRecord record : userWatchedRecordList) {
            max = record.getItemId() > max ? record.getItemId() : max;
            min = record.getItemId() < min ? record.getItemId() : min;
        }
        // 计算item_list边界
        int N_item = max - min + 1; // item元素的个数
        int M_item = 1;
        // 初始化矩阵A
        OpenMapRealMatrix AMatrix = new OpenMapRealMatrix(1, N_item);
        for (RatingRecord record : userWatchedRecordList) {
            AMatrix.setEntry(0, record.getItemId() % N_item, record.getRating());
        }
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
        GetRecommendInfoResponse response = client.GetPlainRecommendInfo(request);
        //  获取AB、BB
        List<String> EnAB = response.getABList();
        List<String> EnBB = response.getBBList();
        //  数据类型转换
        int N_user = response.getN();
        int M_user = response.getM();
        // 计算余弦相似度
        OpenMapRealMatrix cosineMatrix = getCosineSimilarity(M_user, N_user, N_item
                , userWatchedRecordList, AMatrix, EnBB, EnAB);
        //  对应B用户索引列表
        List<String> index_list = new ArrayList<>();
        for (int i = 0; i < 600; i++) {
            index_list.add(Integer.toString(i + 1));
        }
        //  获取排序结果（索引）
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        int[] topList = dataProcessor.Arraysort(cosineMatrix.getRow(0), true);
        // 获取 top 20 Index
        List<String> finalIndex = new ArrayList<>();
        List<String> partCosineSimilarity = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            finalIndex.add(index_list.get(topList[i]));
            partCosineSimilarity.add(Double.toString(cosineMatrix.getRow(0)[topList[i]]));
        }
        //  获取Item列表
        List<Integer> itemIdList = getPlainRecommendList(partCosineSimilarity, finalIndex);
        //  数据更新
        Boolean flag = updateRecommendList(itemIdList, userWatchedRecordList
                , userId, cosineMatrix, topList);
        //  构造返回值
        List<String> demo = new ArrayList<>();
        demo.add("TASK :: ");
        if (flag) demo.add("DONE!!!");
        return demo;
    }

    @Override
    public List<Integer> getPlainRecommendList(List<String> cosineSimilarity, List<String> Index) throws Exception {
        List<Integer> recommendIdList = new ArrayList<>();
        //  构造请求
        GetItemIdRequest request = new GetItemIdRequest();
        request.setIndexList(Index);
        request.setCosineSimilarityList(cosineSimilarity);
        GetItemIdResponse response = client.GetPlainItemId(request);
        //  获取商品推荐数据itemIdList、预计评平分数据ratingList
        List<String> itemIdList = response.getItemIdList();
        List<String> ratingList = response.getRatingList();
        //  过滤ratingList
        List<String> remove_item_list = new ArrayList<>();
        for (String s : ratingList) {
            if (Double.parseDouble(s) <= 0) {
                remove_item_list.add(s);
            }
        }
        log.info("remove_item_list :: "+remove_item_list);
        ratingList.removeAll(remove_item_list);
        if (ratingList.isEmpty()) return recommendIdList;
        //  格式化
        double[] itemSimList = new double[ratingList.size()];
        for (int i = 0; i < ratingList.size(); i++) {
            itemSimList[i] = Double.parseDouble(ratingList.get(i));
        }
        //  排序评分结果
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        int[] itemIndex = dataProcessor.Arraysort(itemSimList, true);
        //  构造返回值
        for (int i = 0; i < itemIndex.length; i++) {
            recommendIdList.add(Integer.parseInt(itemIdList.get(itemIndex[i])));
        }
        log.info("recommendIdList :: " + recommendIdList.toString());
        return recommendIdList;
    }

    public boolean updateRecommendList(List<Integer> itemIdList, List<RatingRecord> userWatchedRecordList
            , int userId, OpenMapRealMatrix cosineMatrix, int[] topList) {
        if (!itemIdList.isEmpty()) {
            //  过滤数据（去除用户看过的）
            for (RatingRecord r : userWatchedRecordList) {
                if (itemIdList.contains(r.getItemId())) {
                    itemIdList.remove(new Integer(r.getItemId()));
                }
            }
        }
        //  数据不足时--填充默认数据 解决冷启动
        if (itemIdList.size() < 20) {
            for (int i = 0; i < DEFAULT_ITEM_LIST.length; i++) {
                itemIdList.add(DEFAULT_ITEM_LIST[i]);
            }
        }
        //  db更新-查询历史数据
        UserRecommendExample userRecommendExample = new UserRecommendExample();
        userRecommendExample.createCriteria().andUserIdEqualTo(userId);
        List<UserRecommend> originUserRecommendList = userRecommendMapper.selectByExample(userRecommendExample);
        int threshold = Math.min(20, itemIdList.size());
        // 更新 db
        log.info("recommendList :: itemIdList :: "+itemIdList);
        if(!originUserRecommendList.isEmpty()){
            for(int i = 0;i < Math.min(threshold,originUserRecommendList.size());i ++){
                UserRecommend userRecommend = new UserRecommend();
                userRecommend.setId(originUserRecommendList.get(i).getId());
                userRecommend.setUserId(userId);
                userRecommend.setItemId(itemIdList.get(i));
                userRecommend.setRating(cosineMatrix.getRow(0)[topList[i]]);
                userRecommendMapper.updateByPrimaryKey(userRecommend);
            }
        }else{
            for (int i = 0; i < threshold; i++) {
                UserRecommend userRecommend = new UserRecommend();
                userRecommend.setUserId(userId);
                userRecommend.setItemId(itemIdList.get(i));
                userRecommend.setRating(cosineMatrix.getRow(0)[topList[i]]);
                userRecommendMapper.insert(userRecommend);
            }
        }
        return true;
    }


    public OpenMapRealMatrix getCosineSimilarity(int M_user, int N_user, int N_item
            , List<RatingRecord> userWatchedRecordList, OpenMapRealMatrix AMatrix
            , List<String> DeBB, List<String> DeAB) {
        //  计算AA矩阵
        OpenMapRealMatrix AAMatrix = (OpenMapRealMatrix) AMatrix.multiply(AMatrix.transpose());
        //  开方
        AAMatrix.setEntry(0,0,Math.sqrt(AAMatrix.getEntry(0,0)));
        //  初始化BB、AB矩阵
        OpenMapRealMatrix BBMatrix = new OpenMapRealMatrix(M_user, N_user);
        OpenMapRealMatrix ABMatrix = new OpenMapRealMatrix(M_user, 1);
        for (int i = 0; i < M_user; i++) {
            for (int j = 0; j < N_user; j++) {
                BBMatrix.setEntry(i, j, Double.parseDouble(DeBB.get(i * N_user + j)));
            }
        }
        for (int i = 0; i < N_user; i++) {
            ABMatrix.setEntry(i, 0, Double.parseDouble(DeAB.get(i)));
        }
        //  计算余弦相似度
        OpenMapRealMatrix cosineMatrix = new OpenMapRealMatrix(1, M_user);
        for (int i = 0; i < M_user; i++) {
            //  分子
            double molecular = ABMatrix.getEntry(i, 0);
            OpenMapRealMatrix BB_path = new OpenMapRealMatrix(1, M_user);
            for (int j = 0; j < M_user; j++) {
                BB_path.setEntry(0, j, BBMatrix.getEntry(i, j));
            }
            //  分母
            double denominator = AAMatrix.multiply(BB_path).getEntry(0, 0);
            if (Double.compare(denominator, 0.0) > 0 && Double.compare(molecular, 0.0) > 0) {
                cosineMatrix.setEntry(0, i, (molecular / denominator));
            }
        }
        log.info("cosineMatrix :: " + cosineMatrix.toString());
        return cosineMatrix;
    }

}
