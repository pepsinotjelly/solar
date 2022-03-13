package com.bubble.service.impl;

import com.bubble.mapper.ItemBaseMapper;
import com.bubble.mapper.RatingRecordMapper;
import com.bubble.model.*;
import com.bubble.service.UserRecommendService;
import com.bubble.mapper.UserRecommendMapper;
import com.bubble.thrift.ThriftProxyFactory;
import com.bubble.thrift.recommend_service.GetRecommendInfoRequest;
import com.bubble.thrift.recommend_service.GetRecommendInfoResponse;
import com.bubble.thrift.recommend_service.RecommendService;
import com.bubble.utils.DataProcessor;
import com.bubble.utils.IdWorker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
    public List<ItemBase> getRecommendList(List<Integer> userIdList) throws Exception {
        List<ItemBase> recommendList = new ArrayList<>();
        // TODO
        //  构造A矩阵
        RatingRecordExample example = new RatingRecordExample();
        example.createCriteria().andIdIn(userIdList);
        List<RatingRecord> recordList = ratingRecordMapper.selectByExample(example);
        int max = 0;
        int min = Integer.MAX_VALUE;
        for (RatingRecord record : recordList) {
            max = record.getItemId() > max ? record.getItemId() : max;
            min = record.getItemId() < min ? record.getItemId() : min;
        }
        int N = max - min;
        int M = userIdList.size();
        double[][] A = new double[M][N];
        for(RatingRecord record : recordList){
            A[record.getUserId()/N][record.getItemId()%N] = record.getRating();
        }
        //  计算AA
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        double[][] AA = dataProcessor.getAMulB(A,A);
        double[] A_one_line = dataProcessor.getVectorCompression(AA);
//        // 明文基础上进行数据掩盖
//        Double[][] masking_1 = dataProcessor.getMaskingMatrix(M,N);
//        for(int i = 0;i < M;i ++){
//            for(int j = 0;j < N;j ++){
//                AA[i][j] = (AA[i][j] + masking_1[i][j]) % 5;
//            }
//        }
//        //  加密
//        //  密文基础上去除掩盖
//        for(int i = 0;i < M;i ++){
//            for(int j = 0;j < N;j ++){
//                AA[i][j] = (AA[i][j] - masking_1[i][j] + 5) % 5;
//            }
//        }
        //  数据转换
        List<String> AList = new ArrayList<>();
        for(double[] a_list: A){
            for(double a : a_list){
                AList.add(Double.toString(a));
            }
        }
        //  获取AB、BB
        GetRecommendInfoRequest request = new GetRecommendInfoRequest();
        request.setAList(AList);
        request.setEndPosition(max);
        request.setStartPosition(min);
        GetRecommendInfoResponse response = client.GetRecommendInfo(request);
        List<String> EnAB = response.getABList();
        List<String> EnBB = response.getBBList();
//        //  密文基础上进行数据掩盖
//        Double[] masking_2 = dataProcessor.getMaskingMatrix(M);
//        for(int i = 0;i < M;i ++){
//            //TODO 调用密文加减法计算函数
//        }

        //  解密AB、BB

        List<String> DeAB = new ArrayList<>();
        List<String> DeBB = new ArrayList<>();
//        //  明文基础上去除数据掩盖
//        for(int i = 0;i < M;i ++){
//            //TODO 调用密文加减法计算函数
//        }
        //  数据类型转换
        double[] BB = new double[M];
        double[] AB = new double[M];
        for(int i = 0;i < M;i ++){
            BB[i] = Double.parseDouble(EnBB.get(i));
            AB[i] = Double.parseDouble(EnAB.get(i));
        }
        //  计算余弦相似度
        double[] cosineSimilarity = dataProcessor.getCosineSimilarity(A_one_line, BB, AB);
        //  获取排序结果
        int[] sortList = dataProcessor.Arraysort(cosineSimilarity,true);
        //  获取Item列表
        List<ItemBase> itemBaseList = null;
        //  获取对应itemInfo
        //  构造返回值
        return recommendList;
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
