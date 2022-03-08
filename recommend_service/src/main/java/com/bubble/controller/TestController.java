package com.bubble.controller;

//import com.bubble.dal.UserInfoService;

import com.bubble.dal.ItemBaseService;
import com.bubble.dal.ScoreRecordService;
import com.bubble.dal.UserBaseService;
import com.bubble.dal.UserInfoService;
import com.bubble.model.ItemBase;
import com.bubble.model.ScoreRecord;
import com.bubble.model.UserBase;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
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
    private UserInfoService userInfoService;
    @Autowired
    private UserBaseService userBaseService;
    @Autowired
    private ItemBaseService itemBaseService;
    @Autowired
    private ScoreRecordService scoreRecordService;

    @GetMapping("/test")
    public String test() {
        return "OK";
    }

    @GetMapping(value = "/initUserBase")
    public String initUserBaseData() throws Exception {
        System.out.println("========================initData===========================");
        for (int i = 601; i <= 1600; i++) {
            System.out.println("=======================" + i + "==============================");
            UserBase userBase = new UserBase();
            userBase.setId(i);
            userBase.setUserName("user_" + i);
            userBase.setPassword("user_" + i);
            userBase.setPhoneNumber("phone_number_" + i);
            userBaseService.insert(userBase);
//            Thread.sleep(100);
        }
        return "DONE";
    }

    @GetMapping(value = "/initItemBase")
    public String initItemBaseData() throws Exception {
        System.out.println("========================initData===========================");
        String filename = "recommend_service/src/main/java/com/bubble/controller/movies.csv";
        File file = new File(filename);
        CSVReader reader = null;
        try{
            reader = new CSVReader(new FileReader(filename));
            String[] lines;
            reader.readNext();
            while((lines = reader.readNext()) != null){
                ItemBase itemBase = new ItemBase();
                System.out.println(lines[0]+"============"+lines[1]+"============"+lines[2]);
                itemBase.setId(Integer.parseInt(lines[0]));

                itemBase.setTitle(lines[1]);
                itemBase.setGenres(lines[2]);
                itemBaseService.insert(itemBase);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
            return "DONE";
        }
    @GetMapping(value = "/initScoreRecord")
    public String initScoreRecord() throws Exception {
        System.out.println("========================initScoreRecord===========================");
        String filename = "recommend_service/src/main/java/com/bubble/controller/ratings.csv";
        CSVReader reader = null;
        try{
            reader = new CSVReader(new FileReader(filename));
            String[] lines;
            reader.readNext();
            while((lines = reader.readNext()) != null){
                ScoreRecord scoreRecord = new ScoreRecord();
                System.out.println(lines[0]+"============"+lines[1]+"============"+lines[2]);
                scoreRecord.setUserId(Integer.parseInt(lines[0]));
                scoreRecord.setItemId(Integer.parseInt(lines[1]));
                scoreRecord.setRating(Double.parseDouble(lines[2]));
                scoreRecordService.insert(scoreRecord);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "DONE";
    }
    @GetMapping(value = "/initScoreRecordTags")
    public String initScoreRecordTags() throws Exception {
        System.out.println("========================initScoreRecordTags===========================");
        String filename = "recommend_service/src/main/java/com/bubble/controller/tags.csv";
        CSVReader reader = null;
        try{
            reader = new CSVReader(new FileReader(filename));
            String[] lines;
            reader.readNext();
            while((lines = reader.readNext()) != null){
                ScoreRecord scoreRecord = new ScoreRecord();
                System.out.println(lines[0]+"============"+lines[1]+"============"+lines[2]);
                scoreRecord.setUserId(Integer.parseInt(lines[0]));
                scoreRecord.setItemId(Integer.parseInt(lines[1]));
                scoreRecord.setRating(Double.parseDouble(lines[2]));
                scoreRecordService.insert(scoreRecord);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return "DONE";
    }

        @GetMapping(value = "/list")
        public List<UserBase> getUserList () throws Exception {
            return userBaseService.getList();
        }
    }
