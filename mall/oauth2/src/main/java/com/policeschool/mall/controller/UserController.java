package com.policeschool.mall.controller;

import com.policeschool.mall.domain.User;
import com.policeschool.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/3.
 */
@RestController("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> lists() {
        System.out.println("enter users");
        return userService.getUsers();
    }
}
