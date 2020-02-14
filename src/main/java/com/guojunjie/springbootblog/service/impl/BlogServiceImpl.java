package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.common.Month;
import com.guojunjie.springbootblog.common.PageInfo;
import com.guojunjie.springbootblog.common.PageQueryUtil;
import com.guojunjie.springbootblog.controller.vo.BlogByMonthVo;
import com.guojunjie.springbootblog.controller.vo.BlogCategoryVo;
import com.guojunjie.springbootblog.controller.vo.BlogTagVo;
import com.guojunjie.springbootblog.dao.*;
import com.guojunjie.springbootblog.entity.*;
import com.guojunjie.springbootblog.service.BlogService;
import com.guojunjie.springbootblog.service.dto.BlogCardDTO;
import com.guojunjie.springbootblog.service.dto.BlogDetailDTO;
import com.guojunjie.springbootblog.service.mapper.BlogCardMapper;
import com.guojunjie.springbootblog.service.mapper.BlogDetailMapper;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author guojunjie
 */
@Service
@CacheConfig(cacheNames = "blog")
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
    UserExtraMapper userExtraMapper;
    @Autowired
    RestHighLevelClient restHighLevelClient;


    @Autowired
    BlogDetailMapper blogDetailMapper;
    @Autowired
    BlogCardMapper blogCardMapper;

    @Override
    public List<Blog> getBlogList(){
        return blogMapper.getBlogListWithStatus(false);
    }

    @Override
    public Blog getBlogById(int blogId){
        return blogMapper.getBlogById(blogId);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "'blogAndCount'"),
            @CacheEvict(key = "'categoryAndCount'"),
            @CacheEvict(key = "'tagAndCount'"),
    })
    public boolean addBlog(Blog blog){
        //1. 确保分类数据真实
        ensureBlogCategory(blog);
        //2. 添加博客记录
        // 添加博客
        int res = blogMapper.addBlog(blog);
        if(res > 0){
            //添加博客成功
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

    /**
     * 确保分类正确
     * @param blog 博客
     */
    private void ensureBlogCategory(Blog blog){
        boolean resCategoryFind = this.findBlogCategoryById(blog.getBlogCategoryId());
        if(resCategoryFind){
            //1. 分类不存在
            //2. 向分类表添加该分类
            BlogCategory blogCategory = this.addBlogCategory(blog.getBlogCategoryName());
            //3 重新填入分类id
            blog.setBlogCategoryId(blogCategory != null ? blogCategory.getCategoryId() : null);
        }
    }

    /**
     * 添加标签与博客的关系
     * @param blogTag 博客标签
     * @param blogId 博客主键
     */
    private void addBlogTagRelation(BlogTag blogTag,int blogId){
        BlogTagRelation blogTagRelation = new BlogTagRelation();
        blogTagRelation.setBlogId(blogId);
        blogTagRelation.setTagId(blogTag.getTagId());
        blogTagRelationMapper.addBlogTagRelation(blogTagRelation);
    }

    /**
     * 判断分类是否存在
     * @param blogCategoryId
     * @return
     */
    private boolean findBlogCategoryById(int blogCategoryId){
        BlogCategory blogCategory = blogCategoryMapper.getCategoryById(blogCategoryId);
        return blogCategory == null;
    }

    /**
     * 添加新分类,添加后的分类对象会携带新的key值
     * @param categoryName
     * @return
     */
    private BlogCategory addBlogCategory(String categoryName){
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setCategoryName(categoryName);
        if(blogCategoryMapper.addCategory(blogCategory) < 1) {
            return null;
        }
        return blogCategory;
    }

    /**
     * 根据博客的标签字段获取所有标签对象
     * @param blogTags 博客标签字符串
     * @return 博客标签的集合
     */
    private List<BlogTag> getAddTags(String blogTags){
        String[] tags = blogTags.split(",");
        List<BlogTag> res = new ArrayList<>();
        for(String s : tags){
            //遍历标签名称
            //获取标签类
            BlogTag blogTag = blogTagMapper.getTagByTagName(s);
            if(blogTag == null) {
                //若标签不存在，向标签表添加数据
                BlogTag blogTagNew = new BlogTag();
                blogTagNew.setTagName(s);
                blogTagMapper.addTag(blogTagNew);
                res.add(blogTagNew);
            }else{
                //若标签存在,直接添加
                res.add(blogTag);
            }
        }
        return res;
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "'blogAndCount'"),
            @CacheEvict(key = "'categoryAndCount'"),
            @CacheEvict(key = "'tagAndCount'"),
    })
    public boolean updateBlog(Blog blogNew){
        //1. 获取原blog
        Blog blogOld = blogMapper.getBlogById(blogNew.getBlogId());
        //2. 分类操作
        if(!(blogNew.getBlogCategoryId().equals(blogOld.getBlogCategoryId()))){
            //新分类
            //2.1 确保分类
            ensureBlogCategory(blogNew);
            //2.2 检查旧分类是否需要删除
            checkCategory(blogOld.getBlogCategoryId());
        }
        //3. 提交更新
        blogNew.setUpdateTime(new Date());
        int res = blogMapper.updateBlog(blogNew);
        if(res > 0){
            //更新成功
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
        int res = blogMapper.getBlogCountByCategoryIdAndStatus(categoryId, false);
        if(res <= 1)
        {
            //2.2.1 删除分类
            blogCategoryMapper.deleteCategoryById(categoryId);
        }
    }

    private void checkTags(List<String> list){
        List<BlogTag> blogTags = new ArrayList<>();
        //4.4 获取所有弃用的标签
        for(String tagName : list){
            BlogTag blogTag = blogTagMapper.getTagByTagName(tagName);
            blogTags.add(blogTag);
        }
        //4.5 判断该标签还被使用
        for(BlogTag blogTag : blogTags){
            List<BlogTagRelation> blogTagRelations = blogTagRelationMapper.getBlogTagRelationByTagId(blogTag.getTagId());
            if(blogTagRelations.size() <= 1){
                //若标签没被引用，则删除该标签
                BlogTagRelation blogTagRelation = blogTagRelations.get(0);
                blogTagMapper.deleteTagById(blogTagRelation.getTagId());
                blogTagRelationMapper.deleteBlogTagRelationById(blogTagRelation.getRelationId());
            }
        }
    }

    private List<String> asArrayList(String[] tags){
        return new ArrayList<>(Arrays.asList(tags));
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
            if(i == list.size() - 1) {
                break;
            }
            sb.append(",");
        }
        return sb.toString();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "'blogAndCount'"),
            @CacheEvict(key = "'categoryAndCount'"),
            @CacheEvict(key = "'tagAndCount'"),
    })
    public boolean deleteBlog(int blogId){
        Blog blog = blogMapper.getBlogById(blogId);
        int i = blogMapper.deleteBlogById(blogId);

        checkCategory(blog.getBlogCategoryId());
        List<String> list = asArrayList(blog.getBlogTags().split(","));
        checkTags(list);
        return i == 1;
    }

    @Override
    public boolean switchBlogStatus(int status, int blogId) {
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
    public PageInfo getBlogByPage(int currentPage) {
        int totalCount = blogMapper.getTotalCount();
        PageQueryUtil pageQueryUtil = new PageQueryUtil(currentPage,totalCount);
        int limit = pageQueryUtil.getLimit();
        int start = pageQueryUtil.getStart();
        int totalPage = pageQueryUtil.getTotalPage();
        List<Blog> data = blogMapper.getBlogByPage(start,limit);


        PageInfo pageInfo = null;
        if(data.size() != 0){
            pageInfo = new PageInfo(blogCardMapper.toDto(data),currentPage,totalPage,limit,totalCount);
        }
        return pageInfo;
    }


    @Override
    public BlogDetailDTO getBlogDetailById(int blogId) {
        Blog blog = blogMapper.getBlogById(blogId);
        Blog previousBlog = blogMapper.getPreviousBlog(blogId);
        Blog nextBlog = blogMapper.getNextBlog(blogId);
        if(blog != null){
            BlogDetailDTO blogDetailDTO = blogDetailMapper.toDto(blog);
            blogDetailDTO.setPreviousBlog(blogCardMapper.toDto(blog));
            blogDetailDTO.setNextBlog(blogCardMapper.toDto(blog));
            if(previousBlog != null){
                blogDetailDTO.setPreviousBlog(blogCardMapper.toDto(previousBlog));
            }
            if(nextBlog != null){
                blogDetailDTO.setNextBlog(blogCardMapper.toDto(nextBlog));
            }
            blog.setBlogVisits(blog.getBlogVisits() + 1);
            blogMapper.updateBlog(blog);
            return blogDetailDTO;
        }
        return null;
    }

    @Override
    @Cacheable(key = "'categoryAndCount'")
    public List<BlogCategoryVo> getCategoryAndCountWithStatus(boolean isPublished) {
        List<BlogCategory> blogCategories = blogCategoryMapper.getAllCategories();
        List<BlogCategoryVo> blogCategoryVoList = new ArrayList<>();
        for(BlogCategory blogCategory : blogCategories){
            BlogCategoryVo blogCategoryVo = new BlogCategoryVo();
            blogCategoryVo.setCategoryId(blogCategory.getCategoryId());
            blogCategoryVo.setCategoryName(blogCategory.getCategoryName());

            int blogCount = blogMapper.getBlogCountByCategoryIdAndStatus(blogCategory.getCategoryId(), isPublished);
            blogCategoryVo.setBlogCount(blogCount);
            blogCategoryVoList.add(blogCategoryVo);
        }
        if(blogCategoryVoList.size() > 0){
            return blogCategoryVoList;
        }
        return null;
    }

    @Override
    public List<BlogCardDTO> getPublishedBlogList() {
        return blogCardMapper.toDto(blogMapper.getBlogListWithStatus(true));
    }

    @Override
    public List<BlogCardDTO> getBlogByCategoryAndStatus(String categoryName) {
        return blogCardMapper.toDto(blogMapper.getBlogByCategoryAndStatus(categoryName));
    }

    @Override
    @Cacheable(key = "'tagAndCount'")
    public List<BlogTagVo> getTagAndCountWithStatus(boolean isPublished) {
        System.out.println("走了该语句");
        // 1. 获取所有标签
        List<BlogTag> blogTags = blogTagMapper.getAllTags();
        List<BlogTagVo> blogTagVos = new ArrayList<>();
        List<Blog> drafts = null;
        // 2. 判断是前台调用还是后台调用，区别发表和草稿
        if(isPublished){
            drafts = blogMapper.getDrafts();
        }
        for(BlogTag blogTag : blogTags){
            // 3. 遍历所有标签，找到每个标签在关系表中的个数
            BlogTagVo blogTagVo = new BlogTagVo();

            blogTagVo.setTagId(blogTag.getTagId());
            blogTagVo.setTagName(blogTag.getTagName());

            int blogCount = blogTagRelationMapper.getBlogTagRelationCountByTagId(blogTag.getTagId(), drafts);
            blogTagVo.setBlogCount(blogCount);
            blogTagVos.add(blogTagVo);
        }
        if(blogTagVos.size() > 0){
            return blogTagVos;
        }
        return null;
    }

    @Override
    public List<BlogCardDTO> getBlogByTagAndStatus(String tagName) {
        BlogTag blogTag = blogTagMapper.getTagByTagName(tagName);
        List<BlogTagRelation> blogTagRelations = blogTagRelationMapper.getBlogTagRelationByTagId(blogTag.getTagId());
        List<Blog> blogList = new ArrayList<>();
        for(BlogTagRelation blogTagRelation : blogTagRelations){
            Blog blog = blogMapper.getBlogByTag(blogTagRelation.getBlogId());
            if(blog != null){
                blogList.add(blog);
            }
        }
        if(blogList.size() > 0){
            return blogCardMapper.toDto(blogList);
        }
        return null;
    }

    @Override
    public List<FriendLink> getFriendLinks() {
        return friendLinkMapper.getAllFriendLinks();
    }

    @Override
    public UserExtra getUserExtra() {
        return userExtraMapper.getUserExtra();
    }

    @Override
    public int getTotalVisits() {
        return blogMapper.getTotalVisits();
    }

    @Override
    @Cacheable(key = "'blogAndCount'")
    public List<BlogByMonthVo> getBlogCountInRecentlySixMonthWithStatus(boolean isPublished) {
        //获取博客数
        List<Blog> blogList = blogMapper.getBlogListWithStatus(isPublished);
        Calendar cal = Calendar.getInstance();
        //获取近6个月的博客的创建数
        //近6个月区间
        //分析：1. 如果当前月份大于等于6，那年份就一样，月份就是[month - 5, month]
        //2. 小于6，年份是[year - 1, year],月份是[7 + month, month]

        //flag为true表示month >= 6
        boolean flag = true;
        //当前月份
        int eMonth = cal.get(Calendar.MONTH) + 1;
        //当前年份
        int eYear = cal.get(Calendar.YEAR);
        int bMonth = eMonth - 5;
        int bYear = eYear;
        //找到月份范围区间
        if (eMonth < 6) {
            bMonth = 7 + eMonth;
            bYear = eYear - 1;
            flag = false;
        }
        List<BlogByMonthVo> blogByMonthVos = new ArrayList<>();
        if (flag) {
            for (int i = bMonth; i <= eMonth; i++) {
                BlogByMonthVo blogByMonthVo = new BlogByMonthVo();
                blogByMonthVo.setMonth(Month.getName(i));
                blogByMonthVos.add(blogByMonthVo);
            }
        } else {
            for (int i = bMonth; i <= 12; i++) {
                BlogByMonthVo blogByMonthVo = new BlogByMonthVo();
                blogByMonthVo.setMonth(Month.getName(i));
                blogByMonthVos.add(blogByMonthVo);
            }
            for (int i = 1; i <= eMonth; i++) {
                BlogByMonthVo blogByMonthVo = new BlogByMonthVo();
                blogByMonthVo.setMonth(Month.getName(i));
                blogByMonthVos.add(blogByMonthVo);
            }
        }
        for (Blog blog : blogList) {
            cal.setTime(blog.getCreateTime());
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;

            if (eYear == year || bYear == year) {
                if (flag) {
                    if (month >= bMonth && month <= eMonth) {
                        //命中
                        for(BlogByMonthVo blogByMonthVo : blogByMonthVos){
                            if(blogByMonthVo.getMonth().equals(Month.getName(month))){
                                blogByMonthVo.setCount(blogByMonthVo.getCount() + 1);
                            }
                        }
                    }
                } else {
                    if (month >= bMonth || month <= eMonth) {
                        for(BlogByMonthVo blogByMonthVo : blogByMonthVos){
                            if(blogByMonthVo.getMonth().equals(Month.getName(month))){
                                blogByMonthVo.setCount(blogByMonthVo.getCount() + 1);
                            }
                        }
                    }
                }
            }
        }
        return blogByMonthVos;
    }

    @Override
    public List<Map<String, Object>> searchBlogByKeywords(String keyWords) {
        //https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-high-search.html
        // 1. 先创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest("index_blog");

        // 2. 构建查询条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 关键字搜索,使用BoolQueryBuilder，部分参考自：https://www.cnblogs.com/sbj-dawn/p/8891419.html
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("blog_title", keyWords);
        MatchPhraseQueryBuilder matchPhraseQueryBuilder2 = QueryBuilders.matchPhraseQuery("blog_content", keyWords);
        boolQueryBuilder.should(matchPhraseQueryBuilder);
        boolQueryBuilder.should(matchPhraseQueryBuilder2);
        searchSourceBuilder.query(boolQueryBuilder);

        // 高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("blog_title");
        highlightBuilder.field(highlightTitle);
        HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("blog_content");
        highlightBuilder.field(highlightContent);
        highlightBuilder.preTags("<span style=\"color:red\">");
        highlightBuilder.postTags("</span>");
        searchSourceBuilder.highlighter(highlightBuilder);
        // 查询条件制约
        // 确认从第0个元素开始查询，默认也是0
        searchSourceBuilder.from(0);
        // 返回的查询结果最大为5，默认是10
        searchSourceBuilder.size(5);
        // 超时限制
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        // 3. 将查询条件注册到请求中
        searchRequest.source(searchSourceBuilder);

        // 4. 获取同步返回结果
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            SearchHit[] searchHits = hits.getHits();
            for(SearchHit hit : searchHits){
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlight1 = highlightFields.get("blog_title");
                HighlightField highlight2 = highlightFields.get("blog_content");
                if(highlight1 != null){
                    Text[] text = highlight1.fragments();
                    sourceAsMap.put("blog_title", arrayToString(text));
                }
                if(highlight2 != null){
                    Text[] text = highlight2.fragments();
                    sourceAsMap.put("blog_content", arrayToString(text));
                }
                list.add(sourceAsMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    private String arrayToString(Text[] array){
        if(array == null || array.length < 1){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(Text text : array) {
            stringBuilder.append(text);
        }
        return stringBuilder.toString();
    }
}