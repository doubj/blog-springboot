package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.BlogTagRelation;
import com.guojunjie.springbootblog.service.dto.BlogWithTagDTO;

import java.util.List;

/**
 * @Date： 2020/4/23 12:17
 * @author： guojunjie
 */
public interface BlogTagRelationService {
    /**
     * 添加博客标签关系
     *
     * @param blogTagRelation 博客标签关系
     */
    void addBlogTagRelation(BlogTagRelation blogTagRelation);

    /**
     * 根据标签ID获取所有关系表记录
     *
     * @param tagId 标签ID
     * @return 关系表记录集合
     */
    List<BlogTagRelation> getBlogTagRelationByTagId(int tagId);

    /**
     * 删除关系表记录
     *
     * @param tagId 标签ID
     * @param blogId 博客ID
     */
    void deleteBlogTagRelationByTagIdAndBlogId(int tagId, int blogId);


    /**
     * 所有标签及其所关联的文章数
     * @return
     */
    List<BlogWithTagDTO> getTagAndCount();
}
