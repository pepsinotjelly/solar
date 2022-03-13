package com.bubble.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public double[][] getAMulB(double[][] A, double[][] B) {
        if (A == null || A.length == 0 || A[0].length == 0) return null;
        if (B == null || B.length == 0 || B[0].length == 0) return null;
        double[][] A_mul_B = new double[A.length][B[0].length];
        for (double[] user : A) {
            for (int i = 0; i < user.length; i++) {
                for (int j = 0; j < B[0].length; j++) {
                    A_mul_B[i][j] = user[i] * B[i][j];
                }

            }
        }

        return A_mul_B;
    }

    //  二维向量压缩成一维
    public double[] getVectorCompression(double[][] A) {
        if (A == null || A.length == 0 || A[0].length == 0) return null;
        double[] result = new double[A.length];
        for (int i = 0; i < A.length; i++) {
            result[i] = A[i][0];
            for (int j = 1; j <= A[0].length - 1; j++) {
                result[i] = result[i] + A[i][j];
            }
        }
        return result;
    }

    // 一维向量元素格式化（开方）
    public double[] getVectorSqrt(double[] A) {
        if (A == null || A.length == 0) return null;
        double[] result = new double[A.length];
        for (int i = 0; i < A.length; i++) {
            result[i] = Math.sqrt(A[i]);
        }
        return result;
    }

    // 余弦相似度
    public double[] getCosineSimilarity(double[] A, double[] B, double[] AB) {
        if (A == null || A.length == 0) return null;
        if (AB == null || AB.length == 0) return null;
        if (B == null || B.length == 0) return null;
        double[] result = new double[A.length];
        for (int i = 0; i < A.length; i++) {
            result[i] = AB[i] / (A[i] * B[i]);
        }
        return result;
    }

    public static int[] Arraysort(double[] arr, boolean desc) {
        double temp;
        int index;
        int k = arr.length;
        int[] Index = new int[k];
        for (int i = 0; i < k; i++) {
            Index[i] = i;
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (desc) {
                    if (arr[j] < arr[j + 1]) {
                        temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;

                        index = Index[j];
                        Index[j] = Index[j + 1];
                        Index[j + 1] = index;
                    }
                } else {
                    if (arr[j] > arr[j + 1]) {
                        temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;

                        index = Index[j];
                        Index[j] = Index[j + 1];
                        Index[j + 1] = index;
                    }
                }
            }
        }
        return Index;
    }
}
