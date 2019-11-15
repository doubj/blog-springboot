package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.BlogCategory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BlogCategoryMapper {

    List<BlogCategory> getAllCategory();

    //Generator
    int deleteByPrimaryKey(Integer categoryId);

    int insert(BlogCategory record);

    int insertSelective(BlogCategory record);

    BlogCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(BlogCategory record);

    int updateByPrimaryKey(BlogCategory record);
}