package com.bubble.utils;

import com.bubble.thrift.SayHelloServiceImpl;
import com.bubble.thrift.test.SayHelloService;
import org.apache.thrift.TException;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/13 8:15 PM
 * @Desc:
 */
public class ServiceDemo {
    public static final int SERVER_PORT = 7090;

    public void start(){
        try {
            System.out.println("============================SERVICE START============================");
            TProcessor tProcessor = new SayHelloService.Processor<SayHelloService.Iface>(new SayHelloServiceImpl());
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tProcessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();
        }catch (TException e){
            System.out.println("============================SERVICE ERROR============================");
            e.printStackTrace();
        }
    }
}
