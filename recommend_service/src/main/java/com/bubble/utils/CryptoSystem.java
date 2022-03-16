package com.bubble.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;
import paillierp.Paillier;
import paillierp.key.KeyGen;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;
import paillierp.zkp.EncryptionZKP;
import paillierp.PartialDecryption;

/**
 * @author : sunpengyu.sonia
 * @date : 2022/3/11 10:18 上午
 * @Desc :
 */
@Slf4j
public class CryptoSystem {

    //  密钥生成
    public PaillierPrivateKey getPrivateKey(){
        //Length of the p, note that n=p.q
        int lengthP=112;
        PaillierPrivateKey privateKey=KeyGen.PaillierKey(lengthP,122333356);
        return privateKey;

    }
    //  加密
    //  coefficient :: 系数，AList :: 待加密项
    public List<String> Encryption(List<String> A,int coefficient,PaillierKey publicKey) {
        Paillier eSystem= new Paillier();
        eSystem.setEncryption(publicKey);
        List<String> EnCodeA = new ArrayList<>();
        BigInteger[] EnA = new BigInteger[A.size()];
        log.info("START ENCRYPTION");
        for(int i = 0;i < A.size();i ++) {
            EnA[i] = eSystem.encrypt(BigInteger.valueOf((long) (Double.parseDouble(A.get(i)) * coefficient)));
            EnCodeA.add(EnA[i].toString());
        }
        log.info("ENCRYPTION DONE");
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
        log.info("START DECRYPTION");
        for(int i = 0;i < AList.size();i ++) {
            A[i] = new BigInteger(AList.get(i));
            DeA[i] = eSystem.decrypt(A[i]);
            DeCodeA.add(Double.toString(Double.parseDouble(DeA[i].toString())/coefficient));
        }
        log.info("DECRYPTION DONE");
        return DeCodeA;
    }
}
