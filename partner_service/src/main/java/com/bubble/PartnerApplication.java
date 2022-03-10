package com.bubble;

import com.bubble.utils.ThriftServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
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
        ApplicationContext context =  SpringApplication.run(PartnerApplication.class, args);
        try{
            ThriftServer thriftService = context.getBean(ThriftServer.class);
            thriftService.start();
        } catch (BeansException e) {
            e.printStackTrace();
        }
    }
}
