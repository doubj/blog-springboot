package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.BlogTagRelation;

public interface BlogTagRelationMapper {
    int deleteByPrimaryKey(Integer relationId);

    int insert(BlogTagRelation record);

    int insertSelective(BlogTagRelation record);

    BlogTagRelation selectByPrimaryKey(Integer relationId);

    int updateByPrimaryKeySelective(BlogTagRelation record);

    int updateByPrimaryKey(BlogTagRelation record);
}