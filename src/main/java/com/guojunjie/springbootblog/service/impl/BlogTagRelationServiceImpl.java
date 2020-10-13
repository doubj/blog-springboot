package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.BlogTagRelationMapper;
import com.guojunjie.springbootblog.entity.BlogTagRelation;
import com.guojunjie.springbootblog.service.BlogTagRelationService;
import com.guojunjie.springbootblog.service.dto.BlogWithTagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date： 2020/4/23 12:17
 * @author： guojunjie
 */
@Service
public class BlogTagRelationServiceImpl implements BlogTagRelationService {

    @Autowired
    BlogTagRelationMapper blogTagRelationMapper;

    @Override
    public void addBlogTagRelation(BlogTagRelation blogTagRelation) {
        blogTagRelationMapper.addBlogTagRelation(blogTagRelation);
    }

    @Override
    public List<BlogTagRelation> getBlogTagRelationByTagId(int tagId) {
        return blogTagRelationMapper.getBlogTagRelationByTagId(tagId);
    }

    @Override
    public void deleteBlogTagRelationByTagIdAndBlogId(int tagId, int blogId) {
        blogTagRelationMapper.deleteBlogTagRelationByTagIdAndBlogId(tagId,blogId);
    }

    @Override
    public List<BlogWithTagDTO> getTagAndCount() {
        return blogTagRelationMapper.getTagAndCount();
    }
}
