package com.bubble.utils;

public class DataProcessor {
//     数据处理器--用与数据处理
    private static DataProcessor dataProcessor;

    private DataProcessor() {
    }

    public static synchronized DataProcessor getDataProcessor() {
        if (dataProcessor == null) {
            dataProcessor = new DataProcessor();
        }
        return dataProcessor;
    }
    // TODO
    //  计算A*A向量
    //  计算B*B向量
    //  向量压缩

}
