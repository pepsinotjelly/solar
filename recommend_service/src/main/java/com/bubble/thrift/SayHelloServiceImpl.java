package com.bubble.thrift;

import com.bubble.biz.SayHelloBiz;
import com.bubble.thrift.BaseResp;
import com.bubble.thrift.test.HelloRequest;
import com.bubble.thrift.test.HelloResponse;
import com.bubble.thrift.test.SayHelloService;
import org.apache.thrift.TException;
import org.springframework.stereotype.Component;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/13 8:07 PM
 * @Desc:
 */
@Component
public class SayHelloServiceImpl implements SayHelloService.Iface {
    SayHelloBiz sayHelloBiz = new SayHelloBiz();

    @Override
    public HelloResponse Visit(HelloRequest helloRequest) throws TException {
        System.out.println("==================catch client===============");
        return sayHelloBiz.Visit(helloRequest);
    }
}
