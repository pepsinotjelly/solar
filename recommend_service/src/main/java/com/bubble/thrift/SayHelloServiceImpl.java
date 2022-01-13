package com.bubble.thrift;

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
        String question = helloRequest.getSayWhat();
        HelloResponse helloResponse = new HelloResponse();
        helloResponse.setAnswerIs("I love you to: "+question);
        BaseResp baseResp = new BaseResp();
        baseResp.setStatusCode(0);
        baseResp.setStatusMsg("OK");
        helloResponse.setBaseResp(baseResp);
        return helloResponse;
    }
}
