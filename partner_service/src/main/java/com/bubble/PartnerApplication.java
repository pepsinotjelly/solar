package com.bubble;

import com.bubble.thrift.RecommendServiceImpl;
import com.bubble.utils.ThriftService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
