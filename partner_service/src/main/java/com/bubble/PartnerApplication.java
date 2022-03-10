package com.bubble;

import com.bubble.thrift.RecommendServiceImpl;
import com.bubble.thrift.recommend_service.RecommendService;
import com.bubble.utils.ThriftService;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.bubble.mapper")
public class PartnerApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(PartnerApplication.class, args);
        ThriftService.start(RecommendServiceImpl.class, 7090);
    }
}
