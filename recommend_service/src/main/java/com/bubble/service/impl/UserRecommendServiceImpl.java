package com.bubble.service.impl;

import com.bubble.mapper.ItemBaseMapper;
import com.bubble.mapper.ItemInfoMapper;
import com.bubble.mapper.RatingRecordMapper;
import com.bubble.model.*;
import com.bubble.service.UserRecommendService;
import com.bubble.mapper.UserRecommendMapper;
import com.bubble.thrift.ThriftProxyFactory;
import com.bubble.thrift.recommend_service.*;
import com.bubble.utils.CryptoSystem;
import com.bubble.utils.DataProcessor;
import com.bubble.utils.IdWorker;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.linear.OpenMapRealMatrix;
import org.springframework.stereotype.Service;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class UserRecommendServiceImpl implements UserRecommendService {
    @Resource
    private UserRecommendMapper userRecommendMapper;
    @Resource
    private RatingRecordMapper ratingRecordMapper;
    @Resource
    private ItemInfoMapper itemInfoMapper;
    @Resource
    private ItemBaseMapper itemBaseMapper;

    private final String[] hostPorts = new String[]{"localhost:7090"};
    private RecommendService.Iface client = (RecommendService.Iface) ThriftProxyFactory.newInstance(RecommendService.class, hostPorts);


    @Override
    public List<String> getSimilarityList(List<Integer> userIdList) throws Exception {
        List<String> cosineSimilarityList = new ArrayList<>();
        // TODO
        //  构造A矩阵
        RatingRecordExample ratingRecordExample = new RatingRecordExample();
        ratingRecordExample.createCriteria().andUserIdEqualTo(userIdList.get(0));
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
        //  计算AA
        OpenMapRealMatrix AAMatrix = new OpenMapRealMatrix(1, 1);
        AAMatrix = (OpenMapRealMatrix) AMatrix.multiply(AMatrix.transpose());
        //  数据转换
        List<String> AList = new ArrayList<>();
        for (double a : AMatrix.getRow(0)) {
            AList.add(Double.toString(a));
        }//  TODO 加密
        CryptoSystem cryptoSystem = new CryptoSystem();
        PaillierPrivateKey privateKey = cryptoSystem.getPrivateKey();
        PaillierKey publicKey = privateKey.getPublicKey();
        List<String> EnA = cryptoSystem.Encryption(AList,10,publicKey);

        //  发送请求
        GetRecommendInfoRequest request = new GetRecommendInfoRequest();
        request.setAList(EnA);
        request.setEndPosition(max);
        request.setStartPosition(min);
        request.setPublicKeyN(publicKey.getN().toString());
        request.setPublicKeyRnd("122333356");
        GetRecommendInfoResponse response = client.GetRecommendInfo(request);
        //获取AB、BB
        List<String> EnAB = response.getABList();
        log.info("EnAB :: "+ EnAB.toString());
        List<String> EnBB = response.getBBList();
        log.info("EnBB :: "+ EnBB.toString());
        //TODO
        //  解密AB、BB
        List<String> DeAB = cryptoSystem.Decryption(EnAB,10,privateKey);
        log.info("DeAB :: "+ DeAB);
        List<String> DeBB = cryptoSystem.Decryption(EnBB,1000,privateKey);
        log.info("DeBB :: "+ DeBB);
        //  数据类型转换
        int N_user = response.getN();
        int M_user = response.getM();
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
        // 计算余弦相似度
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
                cosineSimilarityList.add(Double.toString(cosineMatrix.getEntry(0, i)));
            }
        }
        // 10位小数
        log.info("cosineMatrix :: " + cosineMatrix.toString());
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
        //  过滤数据（去除用户看过的）
        log.info("START FILTERING");
        for (RatingRecord r : userWatchedRecordList) {
            if (itemIdList.contains(r.getItemId())) {
                itemIdList.remove(new Integer(r.getItemId())); // 所以删掉了吗
            }
        }
        //  数据不足时填充默认数据
        if (itemIdList.size() < 20) {

        }
        //  获取对应itemInfo
        ItemInfoExample itemInfoExample = new ItemInfoExample();
        itemInfoExample.createCriteria().andIdIn(itemIdList);
        List<ItemInfo> recommendItemInfoList = itemInfoMapper.selectByExample(itemInfoExample);
        //  构造返回值
        List<String> demo = new ArrayList<>();
        demo.add("DONE!!!");
        return demo;
    }

    @Override
    public List<Integer> getRecommendList(List<String> cosineSimilarity, List<String> Index) throws Exception {
        List<Integer> recommendIdList = new ArrayList<>();
        //  加密余弦相似度
        CryptoSystem cryptoSystem = new CryptoSystem();
        PaillierPrivateKey privateKey = cryptoSystem.getPrivateKey();
        PaillierKey publicKey = privateKey.getPublicKey();
        List<String> EnCS = cryptoSystem.Encryption(cosineSimilarity,1000000000,privateKey);
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
        List<String> DeRatingList = cryptoSystem.Decryption(ratingList,1000000000,privateKey);
        //  格式化
        double[] itemSimList = new double[DeRatingList.size()];
        for (int i = 0; i < DeRatingList.size(); i++) {
            itemSimList[i] = Double.parseDouble(DeRatingList.get(i));
        }
        //  排序评分结果
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        int[] itemIndex = dataProcessor.Arraysort(itemSimList, true);
        //  构造返回值
        for (int i = 0; i < itemIndex.length; i++) {
            recommendIdList.add(Integer.parseInt(itemIdList.get(itemIndex[i])));
        }
        log.info("recommendIdList :: " + recommendIdList);
        return recommendIdList;
    }


    public List<String> getPlainSimilarityList(List<Integer> userIdList) throws Exception {
        List<String> cosineSimilarityList = new ArrayList<>();
        // TODO
        //  构造A矩阵
        RatingRecordExample ratingRecordExample = new RatingRecordExample();
        ratingRecordExample.createCriteria().andUserIdEqualTo(userIdList.get(0));
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
        GetRecommendInfoResponse response = client.GetRecommendInfo(request);
        //获取AB、BB
        List<String> EnAB = response.getABList();
        List<String> EnBB = response.getBBList();
        //  密文基础上进行数据掩盖
        //TODO 调用密文加减法计算函数
        //  解密AB、BB
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
                cosineSimilarityList.add(Double.toString(cosineMatrix.getEntry(0, i)));
            }
        }
        log.info("cosineMatrix :: " + cosineMatrix.toString());
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
        //  过滤数据（去除用户看过的）
        for (RatingRecord r : userWatchedRecordList) {
            if (itemIdList.contains(r.getItemId())) {
                itemIdList.remove(new Integer(r.getItemId())); // 所以删掉了吗
            }
        }
        //  数据不足时填充默认数据
        if (itemIdList.size() < 20) {

        }
        //  获取对应itemInfo
        ItemInfoExample itemInfoExample = new ItemInfoExample();
        itemInfoExample.createCriteria().andIdIn(itemIdList);
        List<ItemInfo> recommendItemInfoList = itemInfoMapper.selectByExample(itemInfoExample);
        //  构造返回值
        List<String> demo = new ArrayList<>();
        demo.add("DONE!!!");
        return demo;
    }


    public List<Integer> getPlainRecommendList(List<String> cosineSimilarity, List<String> Index) throws Exception {
        List<Integer> recommendIdList = new ArrayList<>();
        //  加密余弦相似度
        List<String> EnCS = new ArrayList<>();
        //  构造请求
        GetItemIdRequest request = new GetItemIdRequest();
        request.setIndexList(Index);
        request.setCosineSimilarityList(cosineSimilarity);
        GetItemIdResponse response = client.GetItemId(request);
        //  获取商品推荐数据itemIdList、预计评平分数据ratingList
        List<String> itemIdList = response.getItemIdList();
        List<String> ratingList = response.getRatingList();
        //  解密ratingList
        List<String> DeRatingList = new ArrayList<>();
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
