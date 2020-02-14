package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.BlogCategory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author guojunjie
 */
@Component
public interface BlogCategoryMapper {

    /**
     * 获取所有分类
     * @return
     */
    List<BlogCategory> getAllCategories();

    /**
     * 通过ID删除分类记录
     * @param categoryId
     * @return
     */
    int deleteCategoryById(Integer categoryId);

    /**
     * 插入一条记录
     * @param record
     * @return
     */
    int addCategory(BlogCategory record);

    /**
     * 通过Id获取分类
     * @param categoryId
     * @return
     */
    BlogCategory getCategoryById(Integer categoryId);
}