package com.guojunjie.springbootblog.service.impl;

import com.guojunjie.springbootblog.dao.LogMapper;
import com.guojunjie.springbootblog.entity.Blog;
import com.guojunjie.springbootblog.entity.Log;
import com.guojunjie.springbootblog.service.LogService;
import com.guojunjie.springbootblog.service.dto.LogQueryCriteria;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date： 2020/4/26 11:03
 * @author： guojunjie
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;

    @Override
    @Async
    public void addLog(String ip, ProceedingJoinPoint joinPoint, Log log) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.guojunjie.springbootblog.aop.log.Log aopLog= method.getAnnotation(com.guojunjie.springbootblog.aop.log.Log.class);
        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";
        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }

        log.setDescription(aopLog.value());
        log.setRequestIp(ip);
        log.setMethod(methodName);
        log.setParams(params.toString() + " }");
        logMapper.addLog(log);
    }

    @Override
    public Map<String, Object> getLogListAndCountByQuery(LogQueryCriteria query) {
        List<Log> list = logMapper.getLogListByQuery(query);
        int total = logMapper.getTotalCountByQuery(query);
        Map<String, Object> map = new HashMap<>(2);
        map.put("items", list);
        map.put("total",total);
        return map;
    }

    @Override
    public String getLogById(int id) {
        return logMapper.getLogDetailById(id);
    }

    @Override
    public void deleteLogByType(String logType) {
        logMapper.deleteLogByType(logType);
    }

    @Override
    public int getLogCount() {
        return logMapper.getTotalCount();
    }
}
