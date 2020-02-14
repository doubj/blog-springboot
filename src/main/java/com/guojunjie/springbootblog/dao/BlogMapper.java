package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author guojunjie
 */
@Component
public interface BlogMapper {

    //后台服务

    /**
     * 用是否出版来区分前后台对所有博客的查询
     * @param isPublished
     * @return
     */
    List<Blog> getBlogListWithStatus(@Param("isPublished") boolean isPublished);

    /**
     * 通过分类ID获取该分类ID下的博客数，同样用一个布尔值区分前后台
     * @param blogCategoryId
     * @param isPublished
     * @return
     */
    int getBlogCountByCategoryIdAndStatus(@Param("blogCategoryId")int blogCategoryId,@Param("isPublished")boolean isPublished);

    /**
     * 修改博客状态
     * @param status 原状态
     * @param blogId 博客ID
     * @return
     */
    int setStatus(@Param("status")int status,@Param("blogId")int blogId);

    //前台服务

    /**
     * 获取总博客数
     * @return
     */
    int getTotalCount();

    /**
     * 根据分类名获取博客
     * @param categoryName
     * @return
     */
    List<Blog> getBlogByCategoryAndStatus(String categoryName);

    /**
     * 分页获取博客
     * @param start
     * @param limit
     * @return
     */
    List<Blog> getBlogByPage(@Param("start") int start, @Param("limit") int limit);

    /**
     * 获取上一篇博客
     * @param blogId
     * @return
     */
    Blog getPreviousBlog(int blogId);

    /**
     * 获取下一篇博客
     * @param blogId
     * @return
     */
    Blog getNextBlog(int blogId);

    /**
     * 根据ID获取已发布的博客
     * @param blogId
     * @return
     */
    Blog getBlogByTag(int blogId);

    /**
     * 获取总访问量
     * @return
     */
    int getTotalVisits();

    /**
     * 删除博客
     * @param blogId
     * @return
     */
    int deleteBlogById(Integer blogId);

    /**
     * 添加博客
     * @param record
     * @return
     */
    int addBlog(Blog record);

    /**
     * 通过主键获取博客详情
     * @param blogId
     * @return
     */
    Blog getBlogById(Integer blogId);

    /**
     * 更新博客
     * @param record
     * @return
     */
    int updateBlog(Blog record);

    /**
     * 获取所有草稿（未出版的文章）
     * @return
     */
    List<Blog> getDrafts();
}