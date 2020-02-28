package com.policeschool.mall.service;

import com.policeschool.mall.dao.UserDao;
import com.policeschool.mall.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/4.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getUsers() {
        return userDao.getUsers();
    }

    public User findByUserName(String userName) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return user;
            }
        }
        return null;
    }
}
