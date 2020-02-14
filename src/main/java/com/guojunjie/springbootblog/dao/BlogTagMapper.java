package com.guojunjie.springbootblog.dao;
import com.guojunjie.springbootblog.entity.BlogTag;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @author guojunjie
 */
@Component
public interface BlogTagMapper {

    /**
     * 获取所有标签
     * @return
     */
    List<BlogTag> getAllTags();

    /**
     * 通过标签名称获取标签记录
     * @param tagName 标签名，理论上是唯一的
     * @return
     */
    BlogTag getTagByTagName(String tagName);

    /**
     * 删除标签
     * @param tagId
     * @return
     */
    int deleteTagById(Integer tagId);

    /**
     * 添加标签
     * @param record
     * @return
     */
    int addTag(BlogTag record);

}