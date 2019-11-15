package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.BlogTagRelation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BlogTagRelationMapper {

    List<BlogTagRelation> selectByTagId(int tagId);

    int selectCountByTagId(int tagId);

    //Generator
    int deleteByPrimaryKey(Integer relationId);

    int insert(BlogTagRelation record);

    int insertSelective(BlogTagRelation record);

    BlogTagRelation selectByPrimaryKey(Integer relationId);

    int updateByPrimaryKeySelective(BlogTagRelation record);

    int updateByPrimaryKey(BlogTagRelation record);
}