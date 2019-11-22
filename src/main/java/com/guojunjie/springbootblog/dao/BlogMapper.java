package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BlogMapper {

    List<Blog> selectPostsList();

    int checkCategoryBeUsed(int BlogCategoryId);

    int setStatus(@Param("status")int status,@Param("blogId")int blogId);

    int getTotalCount();



    List<Blog> selectPostByCategoryStatus(String categoryName);

    List<Blog> selectPostByStatus();

    List<Blog> selectPostByPage(@Param("start") int start,@Param("limit") int limit);

    Blog selectPreviousBlog(int blogId);

    Blog selectNextBlog(int blogId);

    Blog selectPostByTag(int blogId);

    int getTotalVisits();

    //Generator
    int deleteByPrimaryKey(Integer blogId);

    int insert(Blog record);

    int insertSelective(Blog record);

    Blog selectByPrimaryKey(Integer blogId);

    int updateByPrimaryKeySelective(Blog record);

    int updateByPrimaryKeyWithBLOBs(Blog record);

    int updateByPrimaryKey(Blog record);
}