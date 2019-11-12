package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.FriendLink;

import java.util.List;

public interface LinkService {
    List<FriendLink> selectLinks();
    boolean addLink(FriendLink friendLink);
    boolean updateLink(FriendLink friendLink);
    boolean deleteLink(int id);
}
