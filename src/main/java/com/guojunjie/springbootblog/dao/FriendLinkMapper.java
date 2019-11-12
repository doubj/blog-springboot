package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.FriendLink;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FriendLinkMapper {

    List<FriendLink> selectAllLinks();

    //Generator
    int deleteByPrimaryKey(Integer linkId);

    int insert(FriendLink record);

    int insertSelective(FriendLink record);

    FriendLink selectByPrimaryKey(Integer linkId);

    int updateByPrimaryKeySelective(FriendLink record);

    int updateByPrimaryKey(FriendLink record);
}