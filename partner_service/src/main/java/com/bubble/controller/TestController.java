package com.bubble.controller;

import com.bubble.utils.DataProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping(value = "/test")
    public String have_a_test(){
        return "Hello sonia!";
    }
    @GetMapping(value = "/data_processorAB")
    public String getAMulB(){
        Double[][] A = {{1.0, 2.0, 3.0},{2.0, 2.0, 2.0}};
        Double[][] B = {{2.0,2.0,2.0},{2.0,2.0,2.0}};
        String result = "cosine_similarity:\n";
        DataProcessor dataProcessor = DataProcessor.getDataProcessor();
        Double[][] path_AB = dataProcessor.getAMulB(A,B);
        Double[][] path_AA = dataProcessor.getAMulB(A,A);
        Double[][] path_BB = dataProcessor.getAMulB(B,B);
        Double[] one_line_AA= dataProcessor.getVectorCompression(path_AA);
        Double[] one_line_AB = dataProcessor.getVectorCompression(path_AB);
        Double[] one_line_BB = dataProcessor.getVectorCompression(path_BB);
        Double[] one_line_B = dataProcessor.getVectorSqrt(one_line_BB);
        Double[] one_line_A = dataProcessor.getVectorSqrt(one_line_AA);
        Double[] cosineSimilarity = dataProcessor.getCosineSimilarity(one_line_A,one_line_B,one_line_AB);
        for(int i = 0;i < one_line_A.length;i ++){
            result = result + " " + cosineSimilarity[i];
        }
        return result;
    }
}
