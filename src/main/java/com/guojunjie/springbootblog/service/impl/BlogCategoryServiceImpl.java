package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.BlogCategoryMapper;
import com.guojunjie.springbootblog.entity.BlogCategory;
import com.guojunjie.springbootblog.service.BlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guojunjie
 */
@Service
public class BlogCategoryServiceImpl implements BlogCategoryService {

   @Autowired
    BlogCategoryMapper blogCategoryMapper;

    @Override
    public List<BlogCategory> getCategories() {
        return blogCategoryMapper.getAllCategories();
    }
}
