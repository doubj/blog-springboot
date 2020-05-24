package com.guojunjie.springbootblog.service.dto;

import lombok.Data;

/**
 * @Date： 2020/4/26 12:11
 * @author： guojunjie
 */
@Data
public class LogQueryCriteria {
    private int page;
    private int limit;
    private String logType;
    private String description;
}
