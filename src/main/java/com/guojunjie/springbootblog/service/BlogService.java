package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.common.PageInfo;
import com.guojunjie.springbootblog.controller.vo.BlogByMonthVo;
import com.guojunjie.springbootblog.controller.vo.BlogCategoryVo;
import com.guojunjie.springbootblog.controller.vo.BlogTagVo;
import com.guojunjie.springbootblog.entity.*;
import com.guojunjie.springbootblog.service.dto.BlogCardDTO;
import com.guojunjie.springbootblog.service.dto.BlogDetailDTO;

import java.util.List;
import java.util.Map;

/**
 * @author guojunjie
 */
public interface BlogService {

    //以下为后台管理页的service服务,需要token验证
    /**
     * 获取数据库中所有的博客
     * @return 博客集合
     */
    List<Blog> getBlogList();

    /**
     * 返回主键为blogId的博客
     * @param blogId 博客Id
     * @return 博客
     */
    Blog getBlogById(int blogId);

    /**
     * 添加一篇博客
     * @param blog 待添加博客
     * @return 添加成功否
     */
    boolean addBlog(Blog blog);

    /**
     * 更新一篇博客
     * @param blogNew 待更新博客
     * @return 更新成功否
     */
    boolean updateBlog(Blog blogNew);

    /**
     * 删除一篇博客
     * @param blogId 待删除博客主键
     * @return 删除成功否
     */
    boolean deleteBlog(int blogId);

    /**
     * 修改博客状态 0 : 未出版 ；1 : 出版
     * @param status 原始状态
     * @param blogId 博客主键
     * @return 修改成功否
     */
    boolean switchBlogStatus(int status, int blogId);


    //以下为前台独享的服务接口

    /**
     * 获取分页博客数据
     * @param currentPage 当前页
     * @return 分页信息和该页博客信息
     */
    PageInfo getBlogByPage(int currentPage);

    /**
     * 获取博客的详细信息，包括分类和标签的信息
     * @param blogId 博客主键
     * @return 博客详细信息
     */
    BlogDetailDTO getBlogDetailById(int blogId);

    /**
     * 获取所有已经出版的博客
     * @return 出版的博客集合
     */
    List<BlogCardDTO> getPublishedBlogList();

    /**
     * 获取某分类下的所有出版博客
     * @param categoryName 分类名
     * @return 博客集合
     */
    List<BlogCardDTO> getBlogByCategoryAndStatus(String categoryName);

    /**
     * 获取某标签下所有出版博客
     * @param tagName 标签名
     * @return 博客集合
     */
    List<BlogCardDTO> getBlogByTagAndStatus(String tagName);


    /**
     * 获取所有友链
     * @return 友链集合
     */
    List<FriendLink> getFriendLinks();

    /**
     * 获取用户的额外信息
     * @return 额外信息
     */
    UserExtra getUserExtra();

    /**
     * 获取总访问量
     * @return 总访问量
     */
    int getTotalVisits();

    //公共的一些service借口

    /**
     * 获取标签和对应数目
     * @param isPublished
     * @return
     */
    List<BlogTagVo> getTagAndCountWithStatus(boolean isPublished);

    /**
     * 获取分类和对应数目
     * @param isPublished 是否出版区分前后台：1. true，已出版对应前台
     * @return
     */
    List<BlogCategoryVo> getCategoryAndCountWithStatus(boolean isPublished);

    /**
     * 获取近6个月的博客数
     * @param isPublished 作用同上
     * @return
     */
    List<BlogByMonthVo> getBlogCountInRecentlySixMonthWithStatus(boolean isPublished);


    /**
     * 使用ElasticSearch进行搜索 7.4.2
     * @param keyWords
     * @return
     */
    List<Map<String, Object>> searchBlogByKeywords(String keyWords);
}
