package com.bubble;

import com.bubble.dal.impl.UserInfoServiceImpl;
import com.bubble.thrift.SayHelloServiceImpl;
import com.bubble.utils.ServiceDemo;
import com.bubble.utils.ThriftServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.bubble.mapper")
public class Application
{
    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class, args);
//        ServiceDemo serviceDemo = new ServiceDemo();
//        serviceDemo.start();
        ThriftServer server = new ThriftServer();
        server.start(new Class[]{SayHelloServiceImpl.class},"localhost",7090);
    }
}
