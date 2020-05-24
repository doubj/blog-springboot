package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.BlogCategory;

import java.util.List;

/**
 * @author guojunjie
 */
public interface BlogCategoryService {
    /**
     * 获取所有分类
     *
     * @return 分类列表
     */
    List<BlogCategory> getCategories();

    /**
     * 通过id获取分类
     *
     * @param blogCategoryId id
     * @return 分类
     */
    BlogCategory getCategoryById(int blogCategoryId);

    /**
     * 添加新分类
     *
     * @param blogCategory 分类
     */
    void addCategory(BlogCategory blogCategory);

    /**
     * 根据ID删除分类
     *
     * @param categoryId id
     */
    void deleteCategoryById(int categoryId);
}
