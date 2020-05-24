package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.BlogTag;

import java.util.List;

/**
 * @author guojunjie
 */
public interface BlogTagService {
    /**
     * 获取所有标签
     * @return
     */
    List<BlogTag> getTags();

    /**
     * 添加标签
     *
     * @param blogTag 博客标签
     */
    void addTag(BlogTag blogTag);

    /**
     * 通过标签名找到标签
     *
     * @param tagName  标签名
     * @return 标签
     */
    BlogTag getTagByTagName(String tagName);

    /**
     * 删除标签
     *
     * @param tagId ID
     */
    void deleteTagById(int tagId);
}
