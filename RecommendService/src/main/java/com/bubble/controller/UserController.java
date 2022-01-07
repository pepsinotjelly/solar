package com.bubble.controller;

import com.bubble.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @Author: sunpengyu.sonia
 * @Date: 2022/1/7 2:28 PM
 * @Desc:
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String userList() throws Exception {

        return "/user/list";
    }
}
