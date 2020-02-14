package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.FriendLink;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author guojunjie
 */
@Component
public interface FriendLinkMapper {

    /**
     * 获取所有友链
     * @return
     */
    List<FriendLink> getAllFriendLinks();

    /**
     * 通过ID删除友链
     * @param linkId
     * @return
     */
    int deleteFriendLinkById(Integer linkId);

    /**
     * 添加友链记录
     * @param record
     * @return
     */
    int addFriendLink(FriendLink record);

    /**
     * 更新友链
     * @param record
     * @return
     */
    int updateFriendLink(FriendLink record);
}