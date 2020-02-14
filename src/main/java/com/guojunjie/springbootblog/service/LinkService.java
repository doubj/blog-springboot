package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.FriendLink;

import java.util.List;

/**
 * @author guojunjie
 */
public interface LinkService {

    /**
     * 获取所有友链
     * @return
     */
    List<FriendLink> getFriendLinks();

    /**
     * 添加友链
     * @param friendLink
     * @return
     */
    boolean addFriendLink(FriendLink friendLink);

    /**
     * 更新友链
     * @param friendLink
     * @return
     */
    boolean updateFriendLink(FriendLink friendLink);

    /**
     * 删除友链
     * @param id
     * @return
     */
    boolean deleteFriendLink(int id);
}
