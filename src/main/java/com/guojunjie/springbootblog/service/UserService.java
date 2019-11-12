package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.User;


public interface UserService {

    User findUserById(int userId);

    User findUserByUsernameAndPassword(String userName,String password);

    boolean updateUser(User user);
}
