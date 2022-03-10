package com.bubble.utils;

import com.bubble.thrift.RecommendServiceImpl;
import com.bubble.thrift.recommend_service.RecommendService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ThriftServer implements Runnable{
    @Autowired
    private RecommendServiceImpl recommendService;
    private static int PORT = 7090;


    public void start() {
        try {
            System.out.println("============================THRIFT-SERVER START============================");
            TProcessor tprocessor = new RecommendService.Processor<RecommendService.Iface>(recommendService);
            TServerTransport serverTransport = new TServerSocket(PORT);
            TBinaryProtocol.Factory protocolFactory = new TBinaryProtocol.Factory();
            TMultiplexedProcessor processors = new TMultiplexedProcessor();
            processors.registerProcessor("RecommendService",tprocessor);
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport); // 初始化参数
            args.protocolFactory(protocolFactory);
            args.processor(processors);
            args.minWorkerThreads(10);
            args.maxWorkerThreads(10);
            TServer server = new TThreadPoolServer(args);
            server.serve();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        start();
    }
}
