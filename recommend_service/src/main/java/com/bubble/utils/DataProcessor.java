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

    public double[] getDiff(int N) {
        double[] datas = new double[4];
        List<Integer> plaintext = new ArrayList<>();
        List<Integer> ciphertext = new ArrayList<>();
        int[] ciphertextList = new int[]{593, 356, 480, 457, 318, 539, 590, 1196, 1198, 527, 595, 2762, 589, 377, 1210, 1291, 110, 1246, 1197, 597, 1704, 11, 2797, 1270, 592, 2028, 50, 1036, 1307, 2268, 541, 380, 1097, 260, 2716, 1265, 1, 2396, 733, 440, 1393, 364, 1584, 165, 47, 2115, 1240, 648, 357, 2791, 588, 1517, 780, 1580, 1610, 1500, 508, 349, 150, 1961, 34, 500, 1784, 1127, 296, 39, 2571, 1101, 2858, 474, 2918, 587, 2000, 454, 1200, 1387, 708, 2916, 2628, 1721, 1214, 367, 1225, 919, 1617, 368, 736, 300, 339, 1090, 1917, 2424, 2144, 832, 1193, 1968, 1259, 7, 1682, 2001, 1527, 1213, 3255, 1302, 161, 608, 1266, 1641, 594, 1079, 62, 1374, 2321, 4022, 3578, 497, 1358, 2539};
        int[] plaintextList = new int[]{593, 356, 480, 457, 318, 539, 590, 1196, 1198, 527, 595, 2762, 589, 377, 1210, 1291, 110, 1246, 1197, 597, 1704, 11, 2797, 1270, 592, 2028, 50, 1036, 1307, 2268, 541, 380, 1097, 260, 2716, 1265, 1, 2396, 733, 440, 1393, 364, 1584, 165, 47, 2115, 1240, 648, 357, 2791, 588, 1517, 780, 1580, 1610, 1500, 508, 349, 150, 1961, 34, 500, 1784, 1127, 296, 39, 2571, 1101, 2858, 474, 2918, 587, 2000, 454, 1200, 1387, 708, 2916, 2628, 1721, 367, 1214, 1225, 1617, 919, 368, 736, 300, 339, 1090, 1917, 2424, 2144, 832, 1193, 1259, 1968, 7, 1682, 2001, 1527, 1213, 3255, 1302, 161, 608, 1266, 1641, 594, 1079, 62, 1374, 2321, 4022, 3578, 497, 1358, 2539};
        int count_same_pos_100 = 0; // 前100个数据中推荐位相同的个数
        int count_same_item_100 = 0; // 前100个数据中推荐相同的个数
        int count_same_pos_all = 0; // 全部数据中推荐位相同的个数
        int count_same_item_all = 0; // 全部数据中推荐相同的个数

        for (int i = 0; i < N; i++) {
            if (plaintextList[i] == ciphertextList[i]) {
                count_same_pos_100++;
            }
            plaintext.add(plaintextList[i]);
            ciphertext.add(ciphertextList[i]);
        }
        for (Integer i : plaintext) {
            if (ciphertext.contains(i)) {
                count_same_item_100++;
            }
        }
        plaintext.removeAll(plaintext);
        ciphertext.removeAll(ciphertext);
        for (int i = 0; i < plaintextList.length; i++) {
            if (plaintextList[i] == ciphertextList[i]) {
                count_same_pos_all++;
            }
            plaintext.add(plaintextList[i]);
            ciphertext.add(ciphertextList[i]);
        }
        for (Integer i : plaintext) {
            if (ciphertext.contains(i)) {
                count_same_item_all++;
            }
        }
        datas[0] = Double.parseDouble(Integer.toString(count_same_pos_100)) / 100;
        datas[1] = Double.parseDouble(Integer.toString(count_same_item_100)) / 100;
        datas[2] = Double.parseDouble(Integer.toString(count_same_pos_all)) / Double.parseDouble(Integer.toString(plaintextList.length));
        datas[3] = Double.parseDouble(Integer.toString(count_same_item_all)) / Double.parseDouble(Integer.toString(plaintextList.length));
        return datas;
    }
}
