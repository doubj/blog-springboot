package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.BlogTagMapper;
import com.guojunjie.springbootblog.entity.BlogCategory;
import com.guojunjie.springbootblog.entity.BlogTag;
import com.guojunjie.springbootblog.service.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogTagServiceImpl implements BlogTagService {

    @Autowired
    BlogTagMapper blogTagMapper;

    @Override
    public List<BlogTag> getTags() {
        return blogTagMapper.getAllTag();
    }
}
