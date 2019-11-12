package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    User selectUserByUsernameAndPassword(@Param("userName") String userName, @Param("password") String password);

    User getUser();

    //generator
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}