package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.UserMapper;
import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.exception.BusinessException;
import com.guojunjie.springbootblog.service.UserService;
import com.guojunjie.springbootblog.service.dto.UserPassDTO;
import com.guojunjie.springbootblog.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


/**
 * @author guojunjie
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(String token) {
        String userName = JWTUtil.verify(token);
        return userMapper.getUser(userName);
    }

    @Override
    public String getUserByUsernameAndPassword(String userName, String password) {
        User user = userMapper.getUserByUsernameAndPassword(userName, password);
        return user == null ? null : user.getUserName();
    }

    @Override
    @Cacheable(key="'info'",cacheNames = "user")
    public User getUserInfo() {
        System.out.println(userMapper.getUniqueUserInfo());
        return userMapper.getUniqueUserInfo();
    }

    @Override
    @CacheEvict(key = "'info'",cacheNames = "user")
    public void updateUser(User user) {
        userMapper.updateUser(user);
    }


    @Override
    public void updatePass(String token, UserPassDTO userPassDTO) {
        String userName = JWTUtil.verify(token);
        String password = userMapper.getPassword(userName);
        if(!password.equals(userPassDTO.getOldPass())){
            throw new BusinessException(400,"修改失败，旧密码错误");
        }
        if(password.equals(userPassDTO.getNewPass())){
            throw new BusinessException(400,"新密码不能与旧密码相同");
        }
        userMapper.updatePassword(userName,userPassDTO.getNewPass());
    }
    private String getPassword(String token) {
        String userName = JWTUtil.verify(token);
        return userMapper.getPassword(userName);
    }
}