package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.FriendLinkMapper;
import com.guojunjie.springbootblog.entity.FriendLink;
import com.guojunjie.springbootblog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guojunjie
 */
@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private FriendLinkMapper friendLinkMapper;


    @Override
    public List<FriendLink> getFriendLinks(){
        return friendLinkMapper.getAllFriendLinks();
    }

    @Override
    public boolean addFriendLink(FriendLink friendLink) {
        return friendLinkMapper.addFriendLink(friendLink) != 0;
    }

    @Override
    public boolean updateFriendLink(FriendLink friendLink) {
        return friendLinkMapper.updateFriendLink(friendLink) != 0;
    }

    @Override
    public boolean deleteFriendLink(int id) {
        return friendLinkMapper.deleteFriendLinkById(id) != 0;
    }
}
