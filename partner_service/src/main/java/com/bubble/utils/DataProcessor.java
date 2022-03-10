package com.bubble.utils;

import java.util.List;

public class DataProcessor {
    //     数据处理器--用于数据处理
    private static DataProcessor dataProcessor;

    private DataProcessor() {
    }

    public static synchronized DataProcessor getDataProcessor() {
        if (dataProcessor == null) {
            dataProcessor = new DataProcessor();
        }
        return dataProcessor;
    }

    //  计算A*B向量
    public Double[][] getAMulB(Double[][] A, Double[][] B) {
        if (A == null || A.length == 0 || A[0].length == 0) return null;
        if (B == null || B.length == 0 || B[0].length == 0) return null;
        Double[][] A_mul_B = new Double[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
                A_mul_B[i][j] = A[i][j] * B[i][j];
            }
        }
        return A_mul_B;
    }

    //  二维向量压缩成一维
    public Double[] getVectorCompression(Double[][] A) {
        if (A == null || A.length == 0 || A[0].length == 0) return null;
        Double[] result = new Double[A.length];
        if (A == null || A.length == 0 || A[0].length == 0) return null;
        for (int i = 0; i < A.length; i++) {
            result[i] = A[i][0];
            for (int j = 1; j <= A[0].length - 1; j++) {
                result[i] = result[i] + A[i][j];
            }
        }
        return result;
    }

    // 一维向量二次方元素格式化为一次方（开方）
    public Double[] getVectorSqrt(Double[] A) {
        if (A == null || A.length == 0) return null;
        Double[] result = new Double[A.length];
        for (int i = 0; i < A.length; i++) {
            result[i] = Math.sqrt(A[i]);
        }
        return result;
    }

    // 计算余弦相似度
    public Double[] getCosineSimilarity(Double[] A, Double[] B, Double[] AB) {
        if (A == null || A.length == 0) return null;
        if (AB == null || AB.length == 0) return null;
        if (B == null || B.length == 0) return null;
        Double[] result = new Double[A.length];
        for (int i = 0; i < A.length; i++) {
            result[i] = AB[i] / (A[i] * B[i]);
        }
        return result;
    }

}
