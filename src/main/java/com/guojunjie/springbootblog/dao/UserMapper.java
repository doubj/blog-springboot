package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author guojunjie
 */
@Component
public interface UserMapper {

    /**
     * 验证登录
     * @param userName
     * @param password
     * @return
     */
    User getUserByUsernameAndPassword(@Param("userName") String userName, @Param("password") String password);

    /**
     * 获取唯一用户
     * @return
     */
    User getUser();

    /**
     * 更新用户
     * @param record
     * @return
     */
    int updateUser(User record);
}