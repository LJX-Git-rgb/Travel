package com.travel.dao;

import com.travel.domain.User;

/**
 * @ClassName UserDao
 * @description:
 * @author: LJX
 * @time: 2020/3/10 17:12
 * @version: 1.0
 */
public interface UserDao {

    User findUserByUserName(String userName);
    User findUserByCode(String code);
    boolean addUser(User user);
    boolean activeUser(User user);
    User userLogin(String userName,String userPwd);
    User findUserByUid(int uid);
}
