package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.FriendLinkMapper;
import com.guojunjie.springbootblog.entity.FriendLink;
import com.guojunjie.springbootblog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {

    @Autowired
    private FriendLinkMapper friendLinkMapper;


    @Override
    public List<FriendLink> selectLinks(){
        return friendLinkMapper.selectAllLinks();
    }

    @Override
    public boolean addLink(FriendLink friendLink) {
        return friendLinkMapper.insertSelective(friendLink) != 0;
    }

    @Override
    public boolean updateLink(FriendLink friendLink) {
        return friendLinkMapper.updateByPrimaryKeySelective(friendLink) != 0;
    }

    @Override
    public boolean deleteLink(int id) {
        return friendLinkMapper.deleteByPrimaryKey(id) != 0;
    }
}
