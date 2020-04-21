package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Date： 2020/4/20 14:48
 * @author： guojunjie
 * TODO
 */
@Component
public interface MessageMapper {

    /**
     * 添加留言
     * @param message
     * @return
     */
    int addMessage(Message message);

    /**
     * 分页获取留言列表
     * @param page 页码
     * @param limit 每页留言中
     * @return
     */
    List<Message> getMessageList(@Param("page") int page,@Param("limit")int limit);

    /**
     * 获取留言表记录数（分页展示信息）
     * @return
     */
    int getTotalCount();
}
