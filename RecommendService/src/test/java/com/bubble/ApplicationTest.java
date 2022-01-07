package com.bubble;

import com.bubble.dao.User;
import com.bubble.mapper.UserMapper;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest{
    @Autowired(required = false)
    private UserMapper userMapper;

    @Test
    void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectByExample();
        userList.forEach(System.out::println);
    }
}
