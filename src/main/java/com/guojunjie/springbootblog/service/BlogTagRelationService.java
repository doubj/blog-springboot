package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.BlogTagRelation;

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
     * 获取标签对应的文章数
     *
     * @param tagId 标签ID
     * @return 文章数
     */
    int getBlogTagRelationCountByTagId(int tagId);
}
