package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.BlogCategory;

import java.util.List;

public interface BlogCategoryService {
    List<BlogCategory> getCategories();
}
