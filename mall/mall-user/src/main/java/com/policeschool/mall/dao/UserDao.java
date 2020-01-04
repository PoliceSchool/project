package com.policeschool.mall.dao;

/**
 * @author: lujingxiao
 * @description:
 * @since:
 * @version:
 * @date: Created in 2020/1/4.
 */

import com.policeschool.mall.domain.User;

import java.util.List;


public interface UserDao {

    List<User> getUsers();
}
