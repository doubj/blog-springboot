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
}
