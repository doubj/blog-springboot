package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.entity.UserExtra;
import com.guojunjie.springbootblog.service.dto.UserDetail;


/**
 * @author guojunjie
 */
public interface UserService {

    /**
     * 获取用户详情
     * @return
     */
    UserDetail getUserDetail();

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    User getUserByUsernameAndPassword(String userName, String password);

    /**
     * 更新账户信息
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 更新用户信息
     * @param userExtra
     * @return
     */
    boolean updateUserExtra(UserExtra userExtra);
}
