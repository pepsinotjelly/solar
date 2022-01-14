package com.bubble.constant.annotations;

import com.bubble.thrift.SayHelloServiceImpl;
import com.bubble.thrift.test.SayHelloService;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/14 3:29 PM
 * @Desc:
 */
public @interface EnableThriftServices {
//    public String TARGET_LOCATION = ""; // Service包存放的位置

}
