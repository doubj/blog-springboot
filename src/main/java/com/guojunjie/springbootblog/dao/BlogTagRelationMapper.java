package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.Blog;
import com.guojunjie.springbootblog.entity.BlogTagRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author guojunjie
 */
@Component
public interface BlogTagRelationMapper {

    /**
     * 通过标签Id获取所有博客标签关系
     * @param tagId 标签表的主键，在关系表中是外检
     * @return 返回博客标签关系对象
     */
    List<BlogTagRelation> getBlogTagRelationByTagId(int tagId);

    /**
     * 通过标签Id标签对应的数量，如果是前台查询，会区分是否出版
     * @param tagId 标签表的主键，在关系表中是外检
     * @return 返回对应的数量
     */
    int getBlogTagRelationCountByTagId(@Param("tagId") int tagId);

    /**
     * 通过Id,删除记录
     * @param relationId 博客标签关系表的主键
     * @return 返回影响的行数
     */
    int deleteBlogTagRelationById(Integer relationId);

    /**
     * 添加博客标签关系记录
     * @param record 关系表记录
     * @return 返回影响的行数
     */
    int addBlogTagRelation(BlogTagRelation record);
}