package com.bubble.controller;

import com.bubble.mapper.ItemBaseBMapper;
import com.bubble.mapper.RatingRecordBMapper;
import com.bubble.mapper.UserBaseBMapper;
import com.bubble.model.ItemBaseB;
import com.bubble.model.RatingRecordB;
import com.bubble.model.UserBaseB;
import com.bubble.service.UserBaseService;
import com.bubble.utils.DataProcessor;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileReader;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private UserBaseService userBaseService;
    @Resource
    private UserBaseBMapper userBaseBMapper;
    @Resource
    private RatingRecordBMapper ratingRecordBMapper;
    @Resource
    private ItemBaseBMapper itemBaseBMapper;


    @GetMapping(value = "/test")
    public String have_a_test() {
        return "Hello sonia!";
    }

    //    @GetMapping(value = "/data_processorAB")
//    public String getAMulB() {
//        Double[][] A = {{1.0, 2.0, 3.0}, {2.0, 2.0, 2.0}};
//        Double[][] B = {{2.0, 2.0, 2.0}, {2.0, 2.0, 2.0}};
//        String result = "cosine_similarity:\n";
//        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
//        Double[][] path_AB = dataProcessor.getAMulB(A, B);
//        Double[][] path_AA = dataProcessor.getAMulB(A, A);
//        Double[][] path_BB = dataProcessor.getAMulB(B, B);
//        Double[] one_line_AA = dataProcessor.getVectorCompression(path_AA);
//        Double[] one_line_AB = dataProcessor.getVectorCompression(path_AB);
//        Double[] one_line_BB = dataProcessor.getVectorCompression(path_BB);
//        Double[] one_line_B = dataProcessor.getVectorSqrt(one_line_BB);
//        Double[] one_line_A = dataProcessor.getVectorSqrt(one_line_AA);
//        Double[] cosineSimilarity = dataProcessor.getCosineSimilarity(one_line_A, one_line_B, one_line_AB);
//        for (int i = 0; i < one_line_A.length; i++) {
//            result = result + " " + cosineSimilarity[i];
//        }
//        return result;
//    }
//    @GetMapping(value = "/initUserBase")
//    public String initUserBaseData() throws Exception {
//        System.out.println("========================initData===========================");
//        for (int i = 1601; i <= 3000; i++) {
//            System.out.println("=======================" + i + "==============================");
//            UserBaseB userBase = new UserBaseB();
//            userBase.setId(i);
//            userBase.setUserName("user_" + i);
//            userBase.setPassword("user_" + i);
//            userBase.setPhoneNumber("phone_number_" + i);
//            userBaseBMapper.insert(userBase);
//        }
//        return "DONE";
//    }

//    @GetMapping(value = "/initScoreRecord")
//    public String initScoreRecord() throws Exception {
//        System.out.println("========================initScoreRecord===========================");
//        String filename = "partner_service/src/main/java/com/bubble/controller/ml-25m/ratings.csv";
//        CSVReader reader = null;
//        try {
//            reader = new CSVReader(new FileReader(filename));
//            String[] lines;
//            reader.readNext();
//            while ((lines = reader.readNext()) != null) {
//                if (Integer.parseInt(lines[1]) > 193609 || Integer.parseInt(lines[1]) < 190183) {
//                    System.out.println("too big!" + Integer.parseInt(lines[1]) + "    " + Integer.parseInt(lines[0]));
//                } else {
//                    if (itemBaseBMapper.selectByPrimaryKey(Integer.parseInt(lines[1])) == null) {
//                        System.out.println("dont have this!");
//                    }else{
////                        if(Integer.parseInt(lines[0]) > 3000){
////                            break;
////                        }
//                        RatingRecordB scoreRecord = new RatingRecordB();
//                        System.out.println(lines[0] + "============" + lines[1] + "============" + lines[2]);
//                        scoreRecord.setUserId(Integer.parseInt(lines[0]) % 3000 + 1);
//                        scoreRecord.setItemId(Integer.parseInt(lines[1]));
//                        scoreRecord.setRating(Double.parseDouble(lines[2]));
//                        ratingRecordBMapper.insert(scoreRecord);
//                    }
//                }
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "DONE";
//    }
}
