package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.service.dto.*;
import com.guojunjie.springbootblog.entity.Blog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author guojunjie
 */
@Component
public interface BlogMapper {

    /**
     * 通过查询条件动态查询
     * @param query 后台的查询对象，包括页码信息和标题、分类、ID正逆序等查询信息
     * @return 返回该页内容
     */
    List<Blog> getBlogByQueryAdmin(BlogListQueryAdmin query);

    /**
     * 返回符合查询条件的总记录数
     * @param query 返回符合查询条件的记录数作为总记录数交给前端计算总页面数
     * @return 总记录数
     */
    int getCountByQuery(BlogListQueryAdmin query);

    /**
     * 纵江湖前台博客展示页获取博客列表，由于设定是无限滚动加载，没用用到分页所以不需要
     * 总记录数来计算总页数，query中也只包含每次加载的博客数6和分类及标签信息
     * 标签的判断是通过mysql中的locate方法
     * todo:目前分类和标签的筛选逻辑是与，也就是是该分类并且有标签数组中的标签，也许可以换成或逻辑
     * @param blogListQuery 查询条件
     * @return 博客集合
     */
    List<Blog> getBlogByQuery(BlogListQuery blogListQuery);

    /**
     * 获取所有博客列表，可以用于统计近6月书写博客数
     * @param isPublished 是否已发表
     * @return 返回所有博客
     */
    List<Blog> getBlogList(@Param("isPublished")Boolean isPublished);

    /**
     * 通过分类ID获取该分类ID下的博客数，可以返回分类和对应的博客数给echart图标展示
     * 注意：不区分是否发表
     * @param blogCategoryId 分类Id
     * @return 该Id对应的博客数
     */
    int getBlogCountByCategoryId(@Param("blogCategoryId")int blogCategoryId);

    /**
     * 修改博客状态
     * @param status 目标状态 发表：'published' 草稿：'draft'  删除: 'deleted'
     * @param blogId 博客ID
     * @return 影响的行数
     */
    int setStatus(@Param("status")String status,@Param("blogId")int blogId);

    /**
     * 添加一篇博客
     * @param record 待博客记录
     * @return 影响行数
     */
    int addBlog(Blog record);

    /**
     * 通过主键获取博客详情
     * @param blogId 博客表主键
     * @return 博客详情
     */
    Blog getBlogById(Integer blogId);

    /**
     * 更新一篇博客
     * @param record 待更新博客记录
     * @return 影响行数
     */
    int updateBlog(Blog record);

    /**
     * 获取总博客数,后台dashboard统计博客总数需要
     * @return 博客总数
     */
    int getTotalCount();


    /**
     * 获取上一篇博客，通过子查询找到倒叙主键id中id<blogId中的第一个（也就是blogId的上一个）
     * @param blogId 当前博客Id
     * @return 上一篇博客记录
     */
    Blog getPreviousBlog(int blogId);

    /**
     * 获取下一篇博客，和上面的方法类似
     * @param blogId 当前博客Id
     * @return 下一篇博客记录
     */
    Blog getNextBlog(int blogId);

    /**
     * 获取总访问量,sum(visits)
     * @return 总访问量
     */
    int getTotalVisits();

    /**
     * 删除博客
     * @param blogId
     * @return
     */
    int deleteBlogById(Integer blogId);

    /**
     * 获取状态为deleted并且更新时间超过15天的文章
     * @param expireTime 过期时间
     * @return
     */
    List<Blog> getNeedDeletedBlogList(String expireTime);

    /**
     * 增加文章访问量
     * @param blogId 博客ID
     * @param incr 增加量
     */
    void incrVisits(@Param("blogId")int blogId, @Param("incr")int incr);

    void addVisits(@Param("blogId") int blogId,@Param("visits")Long visits);

    List<BlogWithMonthDTO> getBlogCountBySixMonth(Date startDate);

    List<BlogWithCategoryDTO> getCategoryAndCount();

}