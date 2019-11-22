package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.common.PageInfo;
import com.guojunjie.springbootblog.common.PageQueryUtil;
import com.guojunjie.springbootblog.controller.vo.BlogCategoryVo;
import com.guojunjie.springbootblog.controller.vo.BlogDetailVo;
import com.guojunjie.springbootblog.controller.vo.BlogTagVo;
import com.guojunjie.springbootblog.dao.*;
import com.guojunjie.springbootblog.entity.*;
import com.guojunjie.springbootblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    BlogCategoryMapper blogCategoryMapper;
    @Autowired
    BlogTagMapper blogTagMapper;
    @Autowired
    BlogTagRelationMapper blogTagRelationMapper;
    @Autowired
    FriendLinkMapper friendLinkMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserExtraMapper userExtraMapper;


    @Override
    public List<Blog> getPostsList(){
        return blogMapper.selectPostsList();
    }

    @Override
    public Blog selectPostsById(int blogId){
        return blogMapper.selectByPrimaryKey(blogId);
    }

    @Override
    public boolean addPost(Blog blog){
        //1. 确保分类数据真实
        ensureBlogCategory(blog);
        //2. 添加博客记录
        //2. 添加博客
        int res = blogMapper.insertSelective(blog);
        if(res > 0){//添加博客成功
            //3. 获取所有添加成功的标签集合
            List<BlogTag> blogTags = this.getAddTags(blog.getBlogTags());
            //4. 向关系表添加数据
            for(BlogTag blogTag : blogTags){
                addBlogTagRelation(blogTag,blog.getBlogId());
            }
            return true;
        }
        return false;
    }

    //确保分类数据
    private void ensureBlogCategory(Blog blog){
        boolean resCategoryFind = this.findBlogCategoryById(blog.getBlogCategoryId());
        if(resCategoryFind == true){//1. 分类不存在
            //2. 向分类表添加该分类
            BlogCategory blogCategory = this.addBlogCategory(blog.getBlogCategoryName());
            //3 重新填入分类id
            blog.setBlogCategoryId(blogCategory.getCategoryId());
        }
    }

    //向关系表添加数据
    private void addBlogTagRelation(BlogTag blogTag,int blogId){
        BlogTagRelation blogTagRelation = new BlogTagRelation();
        blogTagRelation.setBlogId(blogId);
        blogTagRelation.setTagId(blogTag.getTagId());
        blogTagRelationMapper.insertSelective(blogTagRelation);
    }

    //判断分类是否存在
    private boolean findBlogCategoryById(int blogCategoryId){
        BlogCategory blogCategory = blogCategoryMapper.selectByPrimaryKey(blogCategoryId);
        return blogCategory == null;
    }
    //添加新分类
    //添加后的分类对象会携带新的key值
    private BlogCategory addBlogCategory(String categoryName){
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setCategoryName(categoryName);
        if(blogCategoryMapper.insertSelective(blogCategory) < 1)
            return null;
        return blogCategory;
    }

    //确保所有标签，并返回所有标签集合
    private List<BlogTag> getAddTags(String blogTags){
        String[] tags = blogTags.split(",");
        List<BlogTag> res = new ArrayList<>();
        for(String s : tags){
            //遍历标签名称
            //获取标签类
            BlogTag blogTag = blogTagMapper.selectByTagName(s);
            if(blogTag == null) {
                //若标签不存在，向标签表添加数据
                BlogTag blogTagNew = new BlogTag();
                blogTagNew.setTagName(s);
                blogTagMapper.insertSelective(blogTagNew);
                res.add(blogTagNew);
            }else{
                //若标签存在,直接添加
                res.add(blogTag);
            }
        }
        return res;
    }

    @Override
    public boolean updatePost(Blog blogNew){
        //1. 获取原blog
        Blog blogOld = blogMapper.selectByPrimaryKey(blogNew.getBlogId());
        //2. 分类操作
        if(!(blogNew.getBlogCategoryId() == blogOld.getBlogCategoryId())){//新分类
            //2.1 确保分类
            ensureBlogCategory(blogNew);
            //2.2 检查旧分类是否需要删除
            checkCategory(blogOld.getBlogCategoryId());
        }
        //3. 提交更新
        blogNew.setUpdateTime(new Date());
        int res = blogMapper.updateByPrimaryKeySelective(blogNew);
        if(res > 0){//更新成功
            //4. 标签操作
            //4.1 获取新标签和弃用的标签(删除用)
            //这里为什么不使用工具类Arrays的asList方法是因为这个方法返回的List无法进行删除操作
            List<String> oldList = this.asArrayList(blogOld.getBlogTags().split(","));
            List<String> newList = this.asArrayList(blogNew.getBlogTags().split(","));
            removeSameTags(oldList,newList);
            //4.2 修改了标签
            if(newList.size() > 0){
                //4.3 确保新标签，获取新增的标签集合
                String newTags = asString(newList);
                List<BlogTag> blogTags = getAddTags(newTags);
                //4.4 添加新的关系数据
                for(BlogTag blogTag : blogTags){
                    addBlogTagRelation(blogTag, blogNew.getBlogId());
                }
            }
            //4.3 有弃用的标签
            if(oldList.size() > 0){
                checkTags(oldList);
            }
            return true;
        }
        return false;
    }

    private void checkCategory(int categoryId){
        int res = blogMapper.checkCategoryBeUsed(categoryId);
        if(res <= 1)
            //2.2.1 删除分类
            blogCategoryMapper.deleteByPrimaryKey(categoryId);
    }

    private void checkTags(List<String> list){
        List<BlogTag> blogTags = new ArrayList<>();
        //4.4 获取所有弃用的标签
        for(String tagName : list){
            BlogTag blogTag = blogTagMapper.selectByTagName(tagName);
            blogTags.add(blogTag);
        }
        //4.5 判断该标签还被使用
        for(BlogTag blogTag : blogTags){
            List<BlogTagRelation> blogTagRelations = blogTagRelationMapper.selectByTagId(blogTag.getTagId());
            if(blogTagRelations.size() <= 1){
                //若标签没被引用，则删除该标签
                BlogTagRelation blogTagRelation = blogTagRelations.get(0);
                blogTagMapper.deleteByPrimaryKey(blogTagRelation.getTagId());
                blogTagRelationMapper.deleteByPrimaryKey(blogTagRelation.getRelationId());
            }
        }
    }

    private List<String> asArrayList(String[] tags){
        List<String> list = new ArrayList<>();
        for(String s : tags){
            list.add(s);
        }
        return list;
    }

    private void removeSameTags(List<String> oldList,List<String> newList){
        //想要在遍历的同时删除，得使用迭代器
        //for循环会删除不完
        //增强for循环会报错
        Iterator<String> iterator = oldList.iterator();
        while(iterator.hasNext()){
            String s = iterator.next();
            if(newList.contains(s)){
                iterator.remove();
                newList.remove(s);
            }
        }
    }

    private String asString(List<String> list){
        StringBuilder sb = new StringBuilder();
        for(int i = 0 ; i < list.size() ; i++){
            sb.append(list.get(i));
            if(i == list.size() - 1)
                break;
            sb.append(",");
        }
        return sb.toString();
    }

    @Override
    public boolean deletePost(int blogId){
        Blog blog = blogMapper.selectByPrimaryKey(blogId);
        int i = blogMapper.deleteByPrimaryKey(blogId);

        checkCategory(blog.getBlogCategoryId());
        List<String> list = asArrayList(blog.getBlogTags().split(","));
        checkTags(list);
        return i == 1;
    }

    @Override
    public boolean switchStatus(int status, int blogId) {
        return blogMapper.setStatus(getStatus(status),blogId) != 0;
    }

    private int getStatus(int status){
        if(status == 1){
            return 0;
        }
        if(status == 0){
            return 1;
        }
        return -1;
    }

    @Override
    public PageInfo getPostByPage(int currentPage) {
        int totalCount = blogMapper.getTotalCount();
        PageQueryUtil pageQueryUtil = new PageQueryUtil(currentPage,totalCount);
        int limit = pageQueryUtil.getLimit();
        int start = pageQueryUtil.getStart();
        int totalPage = pageQueryUtil.getTotalPage();
        List<Blog> data = blogMapper.selectPostByPage(start,limit);

        PageInfo pageInfo = null;
        if(data.size() != 0){
            pageInfo = new PageInfo(data,currentPage,totalPage,limit,totalCount);
        }
        return pageInfo;
    }


    @Override
    public BlogDetailVo selectPostDetailById(int blogId) {
        Blog blog = blogMapper.selectByPrimaryKey(blogId);

        Blog previousBlog = blogMapper.selectPreviousBlog(blogId);
        Blog nextBlog = blogMapper.selectNextBlog(blogId);
        if(blog != null){
            BlogDetailVo blogDetailVo = new BlogDetailVo();
            blogDetailVo.setBlogId(blog.getBlogId());
            blogDetailVo.setBlogCategoryId(blog.getBlogCategoryId());
            blogDetailVo.setBlogCategoryName(blog.getBlogCategoryName());
            blogDetailVo.setBlogContent(blog.getBlogContent());
            blogDetailVo.setBlogCoverImage(blog.getBlogCoverImage());
            blogDetailVo.setBlogStatus(blog.getBlogStatus());
            blogDetailVo.setBlogTags(blog.getBlogTags());
            blogDetailVo.setBlogTitle(blog.getBlogTitle());
            blogDetailVo.setBlogVisits(blog.getBlogVisits());
            blogDetailVo.setCreateTime(blog.getCreateTime());
            blogDetailVo.setUpdateTime(blog.getUpdateTime());
            if(previousBlog != null){
                blogDetailVo.setPreviousBlog(previousBlog);
            }
            if(nextBlog != null){
                blogDetailVo.setNextBlog(nextBlog);
            }
            blog.setBlogVisits(blog.getBlogVisits() + 1);
            blogMapper.updateByPrimaryKeySelective(blog);
            return blogDetailVo;
        }
        return null;
    }

    @Override
    public List<BlogCategoryVo> getCategories() {
        List<BlogCategory> blogCategories = blogCategoryMapper.getAllCategory();
        List<BlogCategoryVo> blogCategoryVoList = new ArrayList<>();
        for(BlogCategory blogCategory : blogCategories){
            BlogCategoryVo blogCategoryVo = new BlogCategoryVo();
            blogCategoryVo.setCategoryId(blogCategory.getCategoryId());
            blogCategoryVo.setCategoryName(blogCategory.getCategoryName());

            int blogCount = blogMapper.checkCategoryBeUsed(blogCategory.getCategoryId());
            blogCategoryVo.setBlogCount(blogCount);
            blogCategoryVoList.add(blogCategoryVo);
        }
        if(blogCategoryVoList.size() > 0){
            return blogCategoryVoList;
        }
        return null;
    }

    @Override
    public List<Blog> getPublishPostsList() {
        return blogMapper.selectPostByStatus();
    }

    @Override
    public List<Blog> getPostByCategoryStatus(String categoryName) {
        return blogMapper.selectPostByCategoryStatus(categoryName);
    }

    @Override
    public List<BlogTagVo> getTags() {
        List<BlogTag> blogTags = blogTagMapper.getAllTag();
        List<BlogTagVo> blogTagVos = new ArrayList<>();
        for(BlogTag blogTag : blogTags){
            BlogTagVo blogTagVo = new BlogTagVo();

            blogTagVo.setTagId(blogTag.getTagId());
            blogTagVo.setTagName(blogTag.getTagName());

            int blogCount = blogTagRelationMapper.selectCountByTagId(blogTag.getTagId());
            blogTagVo.setBlogCount(blogCount);
            blogTagVos.add(blogTagVo);
        }
        if(blogTagVos.size() > 0){
            return blogTagVos;
        }
        return null;
    }

    @Override
    public List<Blog> getPostByTagStatus(String tagName) {
        BlogTag blogTag = blogTagMapper.selectByTagName(tagName);
        List<BlogTagRelation> blogTagRelations = blogTagRelationMapper.selectByTagId(blogTag.getTagId());
        List<Blog> blogList = new ArrayList<>();
        for(BlogTagRelation blogTagRelation : blogTagRelations){
            Blog blog = blogMapper.selectPostByTag(blogTagRelation.getBlogId());
            if(blog != null){
                blogList.add(blog);
            }
        }
        if(blogList.size() > 0){
            return blogList;
        }
        return null;
    }

    @Override
    public List<FriendLink> getFriendLinks() {
        return friendLinkMapper.selectAllLinks();
    }

    @Override
    public UserExtra getUser() {
        User user = userMapper.getUser();
        return userExtraMapper.findExtraById(user.getUserId());
    }

    @Override
    public int getTotalVisits() {
        return blogMapper.getTotalVisits();
    }
}
