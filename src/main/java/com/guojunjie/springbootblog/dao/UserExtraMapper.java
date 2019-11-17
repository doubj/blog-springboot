package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.UserExtra;
import org.springframework.stereotype.Component;

@Component
public interface UserExtraMapper {

    UserExtra findExtraById(int userId);

    int updateByPrimaryKeySelective(UserExtra record);

    // Generator
    int insert(UserExtra record);

    int insertSelective(UserExtra record);
}