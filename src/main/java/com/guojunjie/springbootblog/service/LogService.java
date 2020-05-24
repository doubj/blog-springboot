package com.guojunjie.springbootblog.service;

import com.guojunjie.springbootblog.entity.Log;
import com.guojunjie.springbootblog.service.dto.LogQueryCriteria;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.Map;

/**
 * @Date： 2020/4/26 10:57
 * @author： guojunjie
 */
public interface LogService {
    /**
     * 添加系统日志
     * @param ip
     * @param joinPoint
     * @param log
     */
    void addLog(String ip, ProceedingJoinPoint joinPoint, Log log);

    /**
     * 获取系统日志
     * @param query
     * @return
     */
    Map<String, Object> getLogListAndCountByQuery(LogQueryCriteria query);

    /**
     * 获取日志详情
     * @param id
     * @return
     */
    String getLogById(int id);

    /**
     * 删除日志
     * @param logType
     */
    void deleteLogByType(String logType);

    /**
     * 获取总日志数
     * @return
     */
    int getLogCount();
}
