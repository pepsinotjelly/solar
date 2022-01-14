package com.bubble.biz;

import com.bubble.thrift.BaseResp;
import com.bubble.thrift.test.HelloRequest;
import com.bubble.thrift.test.HelloResponse;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/14 6:25 PM
 * @Desc:
 */
public class SayHelloBiz {
    public HelloResponse Visit(HelloRequest helloRequest){
        String question = helloRequest.getSayWhat();
        HelloResponse helloResponse = new HelloResponse();
        StringBuilder res = new StringBuilder();
        for(int i = 0;i < helloRequest.times;i ++){
            res.append("i love you!");
        }
        helloResponse.setAnswerIs(res.toString() + " to: "+question);
        BaseResp baseResp = new BaseResp();
        baseResp.setStatusCode(0);
        baseResp.setStatusMsg("OK");
        helloResponse.setBaseResp(baseResp);
        return helloResponse;
    }
}
