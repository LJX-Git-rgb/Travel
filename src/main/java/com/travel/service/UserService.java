package com.travel.service;

import com.travel.domain.User;

public interface UserService {

    boolean regist(User user);
    public boolean activeUser(String code);
    User userLogin(User user);
}
