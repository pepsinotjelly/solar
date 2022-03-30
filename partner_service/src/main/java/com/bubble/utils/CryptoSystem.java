package com.bubble.utils;

import lombok.extern.slf4j.Slf4j;
import paillierp.Paillier;
import paillierp.key.KeyGen;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/15 4:12 下午
 * @Desc :
 */
@Slf4j
public class CryptoSystem {
    //  密钥生成
    //  加密
    //  coefficient :: 系数，AList :: 待加密项
    public List<String> Encryption(List<String> A, int coefficient, PaillierKey publicKey) {
        Paillier eSystem= new Paillier();
        eSystem.setEncryption(publicKey);
        List<String> EnCodeA = new ArrayList<>();
        BigInteger[] EnA = new BigInteger[A.size()];
        for(int i = 0;i < A.size();i ++) {
            EnA[i] = eSystem.encrypt(BigInteger.valueOf((long) (Double.parseDouble(A.get(i)) * coefficient)));
            EnCodeA.add(EnA[i].toString());
            log.info("A["+i+"] :: "+(Double.parseDouble(A.get(i)) * coefficient) +"  EnCodeA["+i+"] :: "+ EnCodeA.get(i));
        }
        return EnCodeA;
    }
    //  解密
    //  AList :: 待解密项
    public List<String> Decryption(List<String> AList,int coefficient,PaillierPrivateKey privateKey) {
        Paillier eSystem= new Paillier();
        eSystem.setDecryptEncrypt(privateKey);
        List<String> DeCodeA = new ArrayList<>();
        BigInteger[] A = new BigInteger[AList.size()];
        BigInteger[] DeA = new BigInteger[AList.size()];
        for(int i = 0;i < AList.size();i ++) {
            A[i] = new BigInteger(AList.get(i));
            DeA[i] = eSystem.decrypt(A[i]);
            log.info("DeA["+i+"] ::"+DeA[i].toString());
            DeCodeA.add(Double.toString(Double.parseDouble(DeA[i].toString())/coefficient));
        }
        return DeCodeA;
    }
}
