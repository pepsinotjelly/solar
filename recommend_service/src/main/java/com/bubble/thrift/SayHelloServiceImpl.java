package com.bubble.thrift;

import com.bubble.thrift.BaseResp;
import com.bubble.thrift.test.HelloRequest;
import com.bubble.thrift.test.HelloResponse;
import com.bubble.thrift.test.SayHelloService;
import org.apache.thrift.TException;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/13 8:07 PM
 * @Desc:
 */
public class SayHelloServiceImpl implements SayHelloService.Iface {

    @Override
    public HelloResponse Visit(HelloRequest helloRequest) throws TException {
        System.out.println("==================catch client===============");
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
