package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.MessageMapper;
import com.guojunjie.springbootblog.entity.Message;
import com.guojunjie.springbootblog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date： 2020/4/20 15:05
 * @author： guojunjie
 * TODO
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageMapper messageMapper;

    @Override
    public int addMessage(Message message) {
        return messageMapper.addMessage(message);
    }

    @Override
    public Map<String, Object> getMessageByPage(int page) {
        final int PAGE_COUNT = 5;
        List<Message> list = messageMapper.getMessageList(page, PAGE_COUNT);
        int total = messageMapper.getTotalCount();
        Map<String, Object> res = new HashMap<>(2);
        res.put("list", list);
        res.put("total", total);
        return res;
    }
}
