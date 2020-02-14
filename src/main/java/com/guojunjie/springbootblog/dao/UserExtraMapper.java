package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.UserExtra;
import org.springframework.stereotype.Component;

/**
 * @author guojunjie
 */
@Component
public interface UserExtraMapper {

    /**
     * 通过ID查询用户信息
     * @return
     */
    UserExtra getUserExtra();

    /**
     * 更新用户信息
     * @param record
     * @return
     */
    int updateUserExtra(UserExtra record);
}