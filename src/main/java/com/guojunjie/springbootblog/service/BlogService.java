package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.service.dto.BlogListQuery;
import com.guojunjie.springbootblog.service.dto.BlogListQueryAdmin;
import com.guojunjie.springbootblog.entity.*;
import com.guojunjie.springbootblog.service.dto.*;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author guojunjie
 */
public interface BlogService {

    // 青衫仗剑Admin后台接口
    /**
     * 返回符合查询条件的文章某页的记录和所有符合条件的记录数量
     * @param query 查询条件
     * @return 返回当前页博客集合和总记录数
     */
    Map<String, Object> getBlogListAndCountByQuery(BlogListQueryAdmin query);

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
     * 修改博客状态
     * @param status draft-草稿 published-发布 deleted-删除
     * @param id 博客Id
     * @return
     */
    boolean modifyBlogStatus(String status, int id);

    /**
     * 获取总访问量
     * @return 总访问量
     */
    int getTotalVisits();

    /**
     * 获取标签和对应博客数目
     * @return
     */
    List<BlogWithTagDTO> getTagAndCount();

    /**
     * 获取分类和对应数目
     * @return
     */
    List<BlogWithCategoryDTO> getCategoryAndCount();

    /**
     * 获取近6个月的博客数
     * @return
     */
    List<BlogWithMonthDTO> getBlogCountInRecentlySixMonth();

    /**
     * 获取总文章数
     * @return
     */
    int getBlogCount();


    // 青衫仗剑前台独享的接口
    /**
     * 获取博客列表
     * @param blogListQuery
     * @return
     */
    List<BlogCardDTO> getBlogByQuery(BlogListQuery blogListQuery);

    /**
     * 获取博客的详细信息，包括分类和标签的信息
     * @param blogId 博客主键
     * @return 博客详细信息
     */
    BlogDetailDTO getBlogDetailById(int blogId);

    /**
     * 使用ElasticSearch进行搜索 7.4.2
     * @param keyWords 关键字
     * @param page 页数
     * @return 返回搜索结果Map
     */
    HashMap<String, Object> searchBlogByKeywords(String keyWords, int page);

    // 以下均是不得不暴露出来的服务，本意是想都private的

    /**
     * 周期的检查状态为'deleted'的博客是否需要被真正删除
     */
    void checkBlogScheduled();

    /**
     * 每月1号重置推荐文章列表
     */
    void resetRecommendScheduled();

    /**
     * 将所有已发表的文章同步到Redis的有序集合中，分数都为1，初始化时会调用
     */
    void setRecommend();

    /**
     * 获取推荐文章数，Redis中的zset键值对，key为:recommendList
     * @return 推荐
     */
    Set<ZSetOperations.TypedTuple<String>> getRecommendList();

    /**
     * 点击文章详情后，异步增加Redis的Zset中文章的分数
     * @param blog
     */
    void incrRecommendScore(Blog blog);

    /**
     * 修改状态为'published'则会添加recommendZset中的member
     * @param blogId
     */
    void addRecommendMember(int blogId);

    /**
     * 修改状态为非'published'，则异步移出recommendZset中的member
     * @param blogId
     */
    void removeRecommendMember(int blogId);

    /**
     * 异步修改zset中的member名
     * @param blogNew
     */
    void updateRecommendTitle(Blog blogNew);
}
