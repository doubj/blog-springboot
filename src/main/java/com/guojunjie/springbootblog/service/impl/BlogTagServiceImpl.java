package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.BlogTagMapper;
import com.guojunjie.springbootblog.entity.BlogTag;
import com.guojunjie.springbootblog.service.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guojunjie
 */
@Service
@CacheConfig(cacheNames = "tags")
public class BlogTagServiceImpl implements BlogTagService {

    @Autowired
    BlogTagMapper blogTagMapper;

    @Override
    @Cacheable(key = "'items'")
    public List<BlogTag> getTags() {
        return blogTagMapper.getAllTags();
    }

    @Override
    @CacheEvict(key = "'items'")
    public void addTag(BlogTag blogTag) {
        blogTagMapper.addTag(blogTag);
    }

    @Override
    public BlogTag getTagByTagName(String tagName) {
        return blogTagMapper.getTagByTagName(tagName);
    }

    @Override
    @CacheEvict(key = "'items'")
    public void deleteTagById(int tagId) {
        blogTagMapper.deleteTagById(tagId);
    }

}
