package com.bubble.controller;

//import com.bubble.dal.UserInfoService;

import com.bubble.model.ItemBase;
import com.bubble.service.ItemBaseService;
import com.bubble.service.UserBaseService;
import com.bubble.model.UserBase;
import com.bubble.service.UserRecommendService;
import com.bubble.utils.CryptoSystem;
import com.bubble.utils.DataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import paillierp.Paillier;
import paillierp.key.KeyGen;
import paillierp.key.PaillierKey;
import paillierp.key.PaillierPrivateKey;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/12 3:43 PM
 * @Desc:
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserBaseService userBaseService;
    @Autowired
    private ItemBaseService itemBaseService;
    @Autowired
    private UserRecommendService userRecommendService;

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    @GetMapping(value = "/list")
    public List<UserBase> getUserList() throws Exception {
        return userBaseService.getList();
    }

    @GetMapping(value = "/syncItemBase")
    public String syncItemBase() throws Exception {
        Boolean result = itemBaseService.SyncItemBase();
        return result.toString();
    }

    @GetMapping(value = "/getRecommendList")
    public List<String> getRecommendList(@RequestParam(value = "user_id") int user_id) throws Exception {

        return userRecommendService.getSimilarityList(user_id);
    }
    @GetMapping(value = "/getPlainRecommendList")
    public List<String> getPlainRecommendList(@RequestParam(value = "user_id") int user_id) throws Exception {
        return userRecommendService.getPlainSimilarityList(user_id);
    }

    @GetMapping(value = "/getDiff")
    public double[] getDiff() throws Exception {
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        return dataProcessor.getDiff(100);
    }

    @GetMapping(value = "/testPaillierList")
    public List<String> testPaillierList() throws Exception {
        CryptoSystem system = new CryptoSystem();
        List<String> strings = new ArrayList<>();
        Paillier esystem= new Paillier();
        PaillierPrivateKey privateKey = system.getPrivateKey();
        PaillierKey publicKey = privateKey.getPublicKey();
        BigInteger n = publicKey.getN();
        PaillierKey key = new PaillierKey(n,122333356);
        esystem.setEncryption(key);
        PaillierPrivateKey privateKey1= KeyGen.PaillierKey(112,122333356);
        for (int i = 0; i < 5; i++) {
            strings.add(Integer.toString(i));
        }

        List<String> result = system.Encryption(strings, 10, privateKey);
        for(int i = 0;i < 5;i ++){
            String s = result.get(i);
            BigInteger c1 = esystem.multiply(new BigInteger(s),new BigInteger("12"));
            BigInteger c2 = esystem.multiply(new BigInteger(s),new BigInteger("2"));
            BigInteger c3 = esystem.multiply(new BigInteger(s),new BigInteger("3"));
            c1 = esystem.add(c1,c2);
            c1 = esystem.add(c1,c3);
            c1 = esystem.add(c2,c1);
            result.add(c1.toString());
        }
        List<String> result2 = system.Decryption(result,10,privateKey);
        return result2;
    }

}
