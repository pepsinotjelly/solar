package com.bubble.controller;

//import com.bubble.dal.UserInfoService;

import com.bubble.mapper.ItemBaseMapper;
import com.bubble.mapper.ItemTagMapper;
import com.bubble.mapper.TagMapper;
import com.bubble.model.*;
import com.bubble.service.ItemService;
import com.bubble.service.RecommendService;
import com.bubble.service.UserService;
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

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

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
    private UserService userService;
    @Autowired
    private ItemService itemBaseService;
    @Autowired
    private RecommendService recommendService;
    @Resource
    private ItemBaseMapper itemBaseMapper;
    @Resource
    private TagMapper tagMapper;
    @Resource
    private ItemTagMapper itemTagMapper;

    @GetMapping("/test")
    public String test() {
        return "OK";
    }


    @GetMapping(value = "/syncItemBase")
    public String syncItemBase() throws Exception {
        Boolean result = itemBaseService.SyncItemBase();
        return result.toString();
    }

    @GetMapping(value = "/getRecommendList")
    public List<String> getRecommendList(@RequestParam(value = "user_id") int user_id) throws Exception {

        return recommendService.getSimilarityList(user_id);
    }

    @GetMapping(value = "/getPlainRecommendList")
    public List<String> getPlainRecommendList(@RequestParam(value = "user_id") int user_id) throws Exception {
        return recommendService.getPlainSimilarityList(user_id);
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
        Paillier esystem = new Paillier();
        PaillierPrivateKey privateKey = system.getPrivateKey();
        PaillierKey publicKey = privateKey.getPublicKey();
        BigInteger n = publicKey.getN();
        PaillierKey key = new PaillierKey(n, 122333356);
        esystem.setEncryption(key);
        PaillierPrivateKey privateKey1 = KeyGen.PaillierKey(112, 122333356);
        for (int i = 0; i < 5; i++) {
            strings.add(Integer.toString(i));
        }

        List<String> result = system.Encryption(strings, 10, privateKey);
        for (int i = 0; i < 5; i++) {
            String s = result.get(i);
            BigInteger c1 = esystem.multiply(new BigInteger(s), new BigInteger("12"));
            BigInteger c2 = esystem.multiply(new BigInteger(s), new BigInteger("2"));
            BigInteger c3 = esystem.multiply(new BigInteger(s), new BigInteger("3"));
            c1 = esystem.add(c1, c2);
            c1 = esystem.add(c1, c3);
            c1 = esystem.add(c2, c1);
            result.add(c1.toString());
        }
        List<String> result2 = system.Decryption(result, 10, privateKey);
        return result2;
    }

}
