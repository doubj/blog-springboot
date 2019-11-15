package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.Blog;
import com.guojunjie.springbootblog.entity.BlogTag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BlogTagMapper {

    List<BlogTag> getAllTag();

    BlogTag selectByTagName(String tagName);

    //Generator
    int deleteByPrimaryKey(Integer tagId);

    int insert(BlogTag record);

    int insertSelective(BlogTag record);

    BlogTag selectByPrimaryKey(Integer tagId);

    int updateByPrimaryKeySelective(BlogTag record);

    int updateByPrimaryKey(BlogTag record);
}