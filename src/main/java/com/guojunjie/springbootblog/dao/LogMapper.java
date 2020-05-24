package com.guojunjie.springbootblog.dao;

import com.guojunjie.springbootblog.entity.Log;
import com.guojunjie.springbootblog.service.dto.LogQueryCriteria;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author guojunjie
 */
@Component
public interface LogMapper {

    /**
     * 添加系统日志
     * @param log
     */
    void addLog(Log log);

    /**
     * 获取系统日志列表
     * @param query
     * @return
     */
    List<Log> getLogListByQuery(LogQueryCriteria query);

    /**
     * 获取总记录数，分页用
     * @param query
     * @return
     */
    int getTotalCountByQuery(LogQueryCriteria query);

    /**
     * 获取日志详情
     * @param id
     * @return
     */
    String getLogDetailById(int id);

    /**
     * 删除某类日志
     * @param logType
     */
    void deleteLogByType(String logType);

    /**
     * 获取总日志数
     * @return
     */
    int getTotalCount();
}