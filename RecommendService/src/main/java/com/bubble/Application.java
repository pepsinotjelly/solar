package com.bubble;

import com.bubble.utils.ServiceDemo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.bubble.mapper")
public class Application
{
    public static void main(String[] args) throws Exception{
        ServiceDemo serviceDemo = new ServiceDemo();
        serviceDemo.start();
        SpringApplication.run(Application.class, args);
    }
}
