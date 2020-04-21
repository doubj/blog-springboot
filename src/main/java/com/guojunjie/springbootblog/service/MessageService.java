package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.Message;

import java.util.Map;

/**
 * @Date： 2020/4/20 15:00
 * @author： guojunjie
 * TODO
 */
public interface MessageService {
    /**
     * 添加留言
     * @param message
     * @return
     */
    int addMessage(Message message);

    /**
     * 根据页码获取留言集合
     * @return
     */
    Map<String, Object> getMessageByPage(int page);
}
