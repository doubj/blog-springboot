package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.common.*;
import com.guojunjie.springbootblog.dao.*;
import com.guojunjie.springbootblog.entity.*;
import com.guojunjie.springbootblog.exception.BusinessException;
import com.guojunjie.springbootblog.service.BlogCategoryService;
import com.guojunjie.springbootblog.service.BlogService;
import com.guojunjie.springbootblog.service.BlogTagRelationService;
import com.guojunjie.springbootblog.service.BlogTagService;
import com.guojunjie.springbootblog.service.dto.*;
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
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author guojunjie
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;
    @Autowired
    BlogCategoryService blogCategoryService;
    @Autowired
    BlogTagService blogTagService;
    // 解决@Async调本类方法无效https://blog.csdn.net/qq_36722648/article/details/102171458
    @Lazy
    @Autowired
    BlogService blogService;
    @Autowired
    BlogTagRelationService blogTagRelationService;
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    private RedisTemplate<String, String> template;
    @Resource(name = "redisTemplate")
    private ZSetOperations<String, String> zsetOps;

    @Autowired
    BlogDetailMapper blogDetailMapper;
    @Autowired
    BlogCardMapper blogCardMapper;

    @Override
    public Blog getBlogById(int blogId) {
        return blogMapper.getBlogById(blogId);
    }

    @Override
    public boolean addBlog(Blog blog) {
        //1. 确保分类数据真实
        ensureBlogCategory(blog);
        //2. 添加博客记录
        // 添加博客
        int res = blogMapper.addBlog(blog);
        if (res > 0) {
            //添加博客成功
            //3. 获取所有添加成功的标签集合
            List<BlogTag> blogTags = this.getAddTags(blog.getBlogTags());
            //4. 向关系表添加数据
            for (BlogTag blogTag : blogTags) {
                addBlogTagRelation(blogTag, blog.getBlogId());
            }
            return true;
        }
        return false;
    }


    private void ensureBlogCategory(Blog blog) {
        if (blogCategoryService.getCategoryById(blog.getBlogCategoryId()) == null) {
            //1. 分类不存在
            //2. 向分类表添加该分类
            BlogCategory blogCategory = this.addBlogCategory(blog.getBlogCategoryName());
            //3 重新填入分类id
            blog.setBlogCategoryId(blogCategory.getCategoryId());
        }
    }

    private void addBlogTagRelation(BlogTag blogTag, int blogId) {
        BlogTagRelation blogTagRelation = new BlogTagRelation();
        blogTagRelation.setBlogId(blogId);
        blogTagRelation.setTagId(blogTag.getTagId());
        blogTagRelationService.addBlogTagRelation(blogTagRelation);
    }

    private BlogCategory addBlogCategory(String categoryName) {
        BlogCategory blogCategory = new BlogCategory();
        blogCategory.setCategoryName(categoryName);
        blogCategoryService.addCategory(blogCategory);
        return blogCategory;
    }

    /**
     * 添加新的标签，并返回所有待添加到关系表的标签集合
     *
     * @param blogTags 标签集合字符串
     * @return 待添加到关系表的标签集合
     */
    private List<BlogTag> getAddTags(String blogTags) {
        String[] tags = blogTags.split(",");
        List<BlogTag> res = new ArrayList<>();
        for (String s : tags) {
            //遍历标签名称
            //获取标签类
            BlogTag blogTag = blogTagService.getTagByTagName(s);
            if (blogTag == null) {
                //若标签不存在，向标签表添加数据
                BlogTag blogTagNew = new BlogTag();
                blogTagNew.setTagName(s);
                blogTagService.addTag(blogTagNew);
                res.add(blogTagNew);
            } else {
                //若标签存在,直接添加
                res.add(blogTag);
            }
        }
        return res;
    }

    @Override
    public boolean updateBlog(Blog blogNew) {
        //1. 获取原blog
        Blog blogOld = blogMapper.getBlogById(blogNew.getBlogId());
        //2. 分类操作
        if (!(blogNew.getBlogCategoryId().equals(blogOld.getBlogCategoryId()))) {
            //新分类
            //2.1 确保分类
            ensureBlogCategory(blogNew);
            //2.2 检查旧分类是否需要删除
            checkCategory(blogOld.getBlogCategoryId());
        }
        //3. 提交更新
        int res = blogMapper.updateBlog(blogNew);
        if (res > 0) {
            //更新成功
            //4. 标签操作
            //4.1 获取新标签和弃用的标签(删除用)
            //这里为什么不使用工具类Arrays的asList方法是因为这个方法返回的List无法进行删除操作
            List<String> oldList = this.asArrayList(blogOld.getBlogTags().split(","));
            List<String> newList = this.asArrayList(blogNew.getBlogTags().split(","));
            removeSameTags(oldList, newList);
            //4.2 有新的标签则添加新标签和新的关系记录
            if (newList.size() > 0) {
                //4.3 确保新标签，获取新增的标签集合
                String newTags = asString(newList);
                List<BlogTag> blogTags = getAddTags(newTags);
                //4.4 添加新的关系数据
                for (BlogTag blogTag : blogTags) {
                    addBlogTagRelation(blogTag, blogNew.getBlogId());
                }
            }
            //4.3 有旧的标签则检查旧标签是否要删除并删除关系记录
            if (oldList.size() > 0) {
                checkTags(oldList, blogNew.getBlogId());
            }
            // 如果改了标题，则需要修改Redis中的推荐数据
            if(!blogNew.getBlogTitle().equals(blogOld.getBlogTitle())){
                blogService.updateRecommendTitle(blogNew);
            }
            return true;
        }
        return false;
    }

    @Override
    @Scheduled(cron = "0 0 4 1/5 * ?")
    @Async
    public void checkBlogScheduled() {
        // 从每月的1号开始，每隔五天在凌晨4点检查一次 0 0 4 1/5 * ?
        // * * * * * ?
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String expireTime = sdf.format(System.currentTimeMillis() - 15 * 24 * 60 * 60 * 1000);
        List<Blog> list = blogMapper.getNeedDeletedBlogList(expireTime);

        // 1.删除表中记录以及弃用的记录
        for (Blog blog : list) {
            // 1.1 删除博客记录
            blogMapper.deleteBlogById(blog.getBlogId());
            // 1.2 删除弃用的分类
            checkCategory(blog.getBlogCategoryId());
            // 1.3 删除弃用的标签和关系记录
            List<String> oldTags = this.asArrayList(blog.getBlogTags().split(","));
            checkTags(oldTags, blog.getBlogId());
        }
        // todo:删除存在云对象上的图片
    }

    private void checkCategory(int categoryId) {
        int res = blogMapper.getBlogCountByCategoryId(categoryId);
        if (res <= 1) {
            //2.2.1 删除分类
            blogCategoryService.deleteCategoryById(categoryId);
        }
    }

    private void checkTags(List<String> list, int blogId) {
        List<BlogTag> blogTags = new ArrayList<>();
        // 4.4 获取所有弃用的标签
        for (String tagName : list) {
            BlogTag blogTag = blogTagService.getTagByTagName(tagName);
            blogTags.add(blogTag);
        }
        // 4.5 判断该标签是否还被使用
        for (BlogTag blogTag : blogTags) {
            List<BlogTagRelation> blogTagRelations = blogTagRelationService.getBlogTagRelationByTagId(blogTag.getTagId());
            if (blogTagRelations.size() <= 1) {
                // 若标签没被引用，则删除该标签
                BlogTagRelation blogTagRelation = blogTagRelations.get(0);
                blogTagService.deleteTagById(blogTagRelation.getTagId());
            }
            // 4.6 删除关系表中不再有关系的记录
            blogTagRelationService.deleteBlogTagRelationByTagIdAndBlogId(blogTag.getTagId(), blogId);
        }
    }

    private List<String> asArrayList(String[] tags) {
        return new ArrayList<>(Arrays.asList(tags));
    }

    private void removeSameTags(List<String> oldList, List<String> newList) {
        //想要在遍历的同时删除，得使用迭代器
        //for循环会删除不完
        //增强for循环会报错
        Iterator<String> iterator = oldList.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (newList.contains(s)) {
                iterator.remove();
                newList.remove(s);
            }
        }
    }

    private String asString(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i == list.size() - 1) {
                break;
            }
            sb.append(",");
        }
        return sb.toString();
    }


    @Override
    public BlogDetailDTO getBlogDetailById(int blogId) {
        Blog blog = blogMapper.getBlogById(blogId);
        Blog previousBlog = blogMapper.getPreviousBlog(blogId);
        Blog nextBlog = blogMapper.getNextBlog(blogId);
        if (blog != null) {
            BlogDetailDTO blogDetailDTO = blogDetailMapper.toDto(blog);
            blogDetailDTO.setPreviousBlog(blogCardMapper.toDto(blog));
            blogDetailDTO.setNextBlog(blogCardMapper.toDto(blog));
            if (previousBlog != null) {
                blogDetailDTO.setPreviousBlog(blogCardMapper.toDto(previousBlog));
            }
            if (nextBlog != null) {
                blogDetailDTO.setNextBlog(blogCardMapper.toDto(nextBlog));
            }
            Long visits = blog.getBlogVisits() + 1;
            blog.setBlogVisits(visits);
            blogMapper.addVisits(blogId,visits);
            // 增加推荐分数
            blogService.incrRecommendScore(blog);
            return blogDetailDTO;
        }
        return null;
    }


    @Override
    @Async
    public void setRecommend() {
        if(template.hasKey(Constants.RECOMMEND_ZSET_KEY)){
            template.delete(Constants.RECOMMEND_ZSET_KEY);
        }
        List<Blog> list = blogMapper.getBlogList(true);
        for(Blog blog : list){
            String member = generateMember(blog);
            zsetOps.add(Constants.RECOMMEND_ZSET_KEY, member,1);
        }
    }

    private String generateMember(Blog blog){
        final String END_MARK = "---doubj";
        StringBuffer sb = new StringBuffer();
        sb.append(blog.getBlogTitle());
        sb.append(END_MARK);
        sb.append(blog.getBlogId());
        return sb.toString();
    }

    @Override
    public Set<ZSetOperations.TypedTuple<String>> getRecommendList() {
        Set<ZSetOperations.TypedTuple<String>> set = zsetOps.reverseRangeWithScores("recommendZset", 0, -1);
        return set;
    }

    @Override
    @Async
    public void incrRecommendScore(Blog blog) {
        String member = generateMember(blog);
        zsetOps.incrementScore(Constants.RECOMMEND_ZSET_KEY, member,2);
    }

    @Override
    @Async
    public void addRecommendMember(int blogId) {
        Blog blog = blogMapper.getBlogById(blogId);
        String member = generateMember(blog);
        zsetOps.add(Constants.RECOMMEND_ZSET_KEY, member,5);
    }

    @Override
    public void removeRecommendMember(int blogId) {
        Blog blog = blogMapper.getBlogById(blogId);
        String member = generateMember(blog);
        zsetOps.remove(Constants.RECOMMEND_ZSET_KEY, member);
    }

    @Override
    @Async
    public void updateRecommendTitle(Blog blogNew) {
        Set<ZSetOperations.TypedTuple<String>> set = zsetOps.reverseRangeWithScores(Constants.RECOMMEND_ZSET_KEY, 0, -1);
        for(ZSetOperations.TypedTuple<String> typedTuple : set){
            String str = typedTuple.getValue();
            int blogId = Integer.valueOf(str.substring(str.lastIndexOf("---doubj") + 1));
            if(blogId == blogNew.getBlogId()){
                zsetOps.remove(Constants.RECOMMEND_ZSET_KEY,str);
                String member = generateMember(blogNew);
                zsetOps.add(Constants.RECOMMEND_ZSET_KEY, member,1);
            }
        }
    }

    /**
     * 每月1号重置推荐文章
     */
    @Override
    @Scheduled(cron = "0 0 0 1 * ? ")
    @Async
    public void resetRecommendScheduled() {
        template.delete(Constants.RECOMMEND_ZSET_KEY);
        setRecommend();
    }

    @Override
    public List<BlogWithCategoryDTO> getCategoryAndCount() {
        List<BlogCategory> blogCategories = blogCategoryService.getCategories();
        List<BlogWithCategoryDTO> res = new ArrayList<>();
        for (BlogCategory blogCategory : blogCategories) {
            BlogWithCategoryDTO blogWithCategoryDTO = new BlogWithCategoryDTO();
            blogWithCategoryDTO.setCategoryId(blogCategory.getCategoryId());
            blogWithCategoryDTO.setCategoryName(blogCategory.getCategoryName());

            int blogCount = blogMapper.getBlogCountByCategoryId(blogCategory.getCategoryId());
            blogWithCategoryDTO.setBlogCount(blogCount);
            res.add(blogWithCategoryDTO);
        }
        if (res.size() > 0) {
            return res;
        }
        return null;
    }

    @Override
    public List<BlogWithTagDTO> getTagAndCount() {
        // 1. 获取所有标签
        List<BlogTag> blogTags = blogTagService.getTags();
        List<BlogWithTagDTO> res = new ArrayList<>();

        for (BlogTag blogTag : blogTags) {
            // 2. 遍历所有标签，找到每个标签在关系表中的个数
            BlogWithTagDTO blogWithTagDTO = new BlogWithTagDTO();

            blogWithTagDTO.setTagId(blogTag.getTagId());
            blogWithTagDTO.setTagName(blogTag.getTagName());

            int blogCount = blogTagRelationService.getBlogTagRelationCountByTagId(blogTag.getTagId());
            blogWithTagDTO.setBlogCount(blogCount);
            res.add(blogWithTagDTO);
        }
        if (res.size() > 0) {
            return res;
        }
        return null;
    }

    @Override
    public int getTotalVisits() {
        return blogMapper.getTotalVisits();
    }

    @Override
    public List<BlogWithMonthDTO> getBlogCountInRecentlySixMonth() {
        //获取博客数
        List<Blog> blogList = blogMapper.getBlogList(false);
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
        List<BlogWithMonthDTO> res = new ArrayList<>();
        if (flag) {
            for (int i = bMonth; i <= eMonth; i++) {
                BlogWithMonthDTO blogWithMonthDTO = new BlogWithMonthDTO();
                blogWithMonthDTO.setMonth(Month.getName(i));
                res.add(blogWithMonthDTO);
            }
        } else {
            for (int i = bMonth; i <= 12; i++) {
                BlogWithMonthDTO blogWithMonthDTO = new BlogWithMonthDTO(Month.getName(i), 0);
                res.add(blogWithMonthDTO);
            }
            for (int i = 1; i <= eMonth; i++) {
                BlogWithMonthDTO blogWithMonthDTO = new BlogWithMonthDTO(Month.getName(i), 0);
                blogWithMonthDTO.setMonth(Month.getName(i));
                res.add(blogWithMonthDTO);
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
                        for (BlogWithMonthDTO blogWithMonthDTO : res) {
                            if (blogWithMonthDTO.getMonth().equals(Month.getName(month))) {
                                blogWithMonthDTO.setCount(blogWithMonthDTO.getCount() + 1);
                            }
                        }
                    }
                } else {
                    if (month >= bMonth || month <= eMonth) {
                        for (BlogWithMonthDTO blogWithMonthDTO : res) {
                            if (blogWithMonthDTO.getMonth().equals(Month.getName(month))) {
                                blogWithMonthDTO.setCount(blogWithMonthDTO.getCount() + 1);
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    @Override
    public HashMap<String, Object> searchBlogByKeywords(String keyWords, int page) {
        final int SEARCH_PAGE_COUNT = 5;
        //https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-high-search.html
        // 1. 创建RESTFUL请求体，并把高亮显示设置进去
        // 1.1高亮显示
        HighlightBuilder highlightBuilder = new HighlightBuilder().
                field("blog_title").field("blog_content").fragmentSize(150).numOfFragments(1).noMatchSize(150)
                .preTags("<span style=\"color:red\">").postTags("</span>");

        // 1.2关键字搜索,使用BoolQueryBuilder，部分参考自：https://www.cnblogs.com/sbj-dawn/p/8891419.html
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        MatchPhraseQueryBuilder matchPhraseQueryBuilder = QueryBuilders.matchPhraseQuery("blog_title", keyWords);
        MatchPhraseQueryBuilder matchPhraseQueryBuilder2 = QueryBuilders.matchPhraseQuery("blog_content", keyWords);
        boolQueryBuilder.should(matchPhraseQueryBuilder);
        boolQueryBuilder.should(matchPhraseQueryBuilder2);
        // 查询源
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().from((page - 1) * SEARCH_PAGE_COUNT).size(SEARCH_PAGE_COUNT)
                .timeout(new TimeValue(60, TimeUnit.SECONDS)).highlighter(highlightBuilder).query(boolQueryBuilder);

        // 2. 将查询条件注册到请求中
        SearchRequest searchRequest = new SearchRequest("index_blog").source(searchSourceBuilder);

        // 3. 获取同步返回结果
        ArrayList<BlogSearchDTO> list = new ArrayList<>();
        long total = 0;
        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = searchResponse.getHits();
            total = hits.getTotalHits().value;
            SearchHit[] searchHits = hits.getHits();
            for (SearchHit hit : searchHits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                HighlightField highlight1 = highlightFields.get("blog_title");
                HighlightField highlight2 = highlightFields.get("blog_content");
                if (highlight1 != null) {
                    Text[] text = highlight1.fragments();
                    sourceAsMap.put("blog_title", arrayToString(text));
                }
                if (highlight2 != null) {
                    Text[] text = highlight2.fragments();
                    sourceAsMap.put("blog_content", arrayToString(text));
                }
                BlogSearchDTO blogSearchDTO = new BlogSearchDTO((Integer) sourceAsMap.get("blog_id"),
                        (String) sourceAsMap.get("blog_content"),
                        (String) sourceAsMap.get("blog_title"),
                        (String) sourceAsMap.get("blog_category_name"));
                if ("published".equals(sourceAsMap.get("blog_status"))) {
                    list.add(blogSearchDTO);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("data", list);
        map.put("total", total);
        return map;
    }


    @Override
    public int getBlogCount() {
        return blogMapper.getTotalCount();
    }

    @Override
    public List<BlogCardDTO> getBlogByQuery(BlogListQuery blogListQuery) {
        return blogCardMapper.toDto(blogMapper.getBlogByQuery(blogListQuery));
    }

    @Override
    public Map<String, Object> getBlogListAndCountByQuery(BlogListQueryAdmin query) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("items", getBlogByQuery(query));
        map.put("total", getCountByQuery(query));
        return map;
    }

    @Override
    public boolean modifyBlogStatus(String status, int id) {
        if("published".equals(status)){
            blogService.addRecommendMember(id);
        } else{
            blogService.removeRecommendMember(id);
        }
        return blogMapper.setStatus(status, id) != 0;
    }

    private List<Blog> getBlogByQuery(BlogListQueryAdmin query) {
        // 设置MyBatis分页查询的开始位置，该位置的值等于(page-1) * limit
        return blogMapper.getBlogByQueryAdmin(query);
    }

    private int getCountByQuery(BlogListQueryAdmin query) {
        return blogMapper.getCountByQuery(query);
    }

    private String arrayToString(Text[] array) {
        if (array == null || array.length < 1) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Text text : array) {
            stringBuilder.append(text);
        }
        return stringBuilder.toString();
    }
}