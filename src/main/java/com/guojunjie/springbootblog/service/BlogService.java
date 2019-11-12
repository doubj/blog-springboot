package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.common.PageInfo;
import com.guojunjie.springbootblog.controller.vo.BlogCategoryVo;
import com.guojunjie.springbootblog.controller.vo.BlogDetailVo;
import com.guojunjie.springbootblog.controller.vo.BlogTagVo;
import com.guojunjie.springbootblog.entity.Blog;
import com.guojunjie.springbootblog.entity.BlogCategory;
import com.guojunjie.springbootblog.entity.FriendLink;
import com.guojunjie.springbootblog.entity.User;

import java.util.List;

public interface BlogService {
    List<Blog> getPostsList();
    Blog selectPostsById(int blogId);
    boolean addPost(Blog blog);
    boolean updatePost(Blog blogNew);
    boolean deletePost(int blogId);
    boolean switchStatus(int status, int blogId);


    //blog
    PageInfo getPostByPage(int currentPage);
    BlogDetailVo selectPostDetailById(int blogId);
    List<BlogCategoryVo> getCategories();
    List<Blog> getPublishPostsList();
    List<Blog> getPostByCategoryStatus(int categoryId);
    List<BlogTagVo> getTags();
    List<Blog> getPostByTagStatus(int tagId);
    List<FriendLink> getFriendLinks();
    User getUser();
}
