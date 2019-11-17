package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.controller.vo.UserVo;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.entity.UserExtra;


public interface UserService {

    UserVo findUserById(int userId);

    User findUserByUsernameAndPassword(String userName,String password);

    boolean updateUser(User user);

    boolean updateUserExtra(UserExtra userExtra);
}
