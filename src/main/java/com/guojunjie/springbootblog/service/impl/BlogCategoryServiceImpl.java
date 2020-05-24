package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.BlogCategoryMapper;
import com.guojunjie.springbootblog.entity.BlogCategory;
import com.guojunjie.springbootblog.service.BlogCategoryService;
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
@CacheConfig(cacheNames = "categories")
public class BlogCategoryServiceImpl implements BlogCategoryService {

   @Autowired
    BlogCategoryMapper blogCategoryMapper;

    @Override
    @Cacheable(key = "'items'")
    public List<BlogCategory> getCategories() {
        return blogCategoryMapper.getAllCategories();
    }

    @Override
    public BlogCategory getCategoryById(int blogCategoryId) {
        return blogCategoryMapper.getCategoryById(blogCategoryId);
    }

    @Override
    @CacheEvict(key = "'items'")
    public void addCategory(BlogCategory blogCategory) {
        blogCategoryMapper.addCategory(blogCategory);
    }

    @Override
    @CacheEvict(key = "'items'")
    public void deleteCategoryById(int categoryId) {
        blogCategoryMapper.deleteCategoryById(categoryId);
    }
}
