package com.bubble.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import paillierp.key.KeyGen;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;
import paillierp.zkp.EncryptionZKP;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/11 10:18 上午
 * @Desc :
 */
public class CryptoSystem {

    //TODO
    // 加密
    public List<String> Encryption(Double[][] A) {
        List<String> result = new ArrayList<>();
        KeyGen keyGen = new KeyGen();
        PaillierPrivateKey privateKey = keyGen.PaillierKey(11, 463723);
        BigInteger[][] A_list = new BigInteger[A.length][A[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[0].length; j++) {
            }
        }

        return result;
    }
    //TODO
    // 解密
    // 密钥生成
}
