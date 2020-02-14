package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.BlogCategory;

import java.util.List;

/**
 * @author guojunjie
 */
public interface BlogCategoryService {
    /**
     * 获取所有分类
     * @return
     */
    List<BlogCategory> getCategories();
}
