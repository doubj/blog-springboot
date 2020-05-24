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
     * 根据Token解析出来的userName查询用户
     * @param userName 用户名
     * @return
     */
    User getUser(String userName);

    /**
     * 更新：待修改，暂时弃用
     * @param record
     * @return
     */
    int updateUser(User record);

    /**
     * 获取唯一的用户信息
     * @return
     */
    User getUniqueUserInfo();

    /**
     * 获取密码
     * @param userName
     * @return
     */
    String getPassword(String userName);

    /**
     * 更新密码
     * @param userName
     * @param newPass
     */
    void updatePassword(@Param("userName")String userName, @Param("password")String newPass);
}