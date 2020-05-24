package com.guojunjie.springbootblog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Date： 2020/4/26 9:59
 * @author： guojunjie
 */
@Data
@NoArgsConstructor
public class Log {
    private Integer id;
    private String description;
    private String method;
    private String params;
    private String logType;
    private String requestIp;
    private Long time;
    private String exceptionDetail;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    public Log(String logType, Long time) {
        this.logType = logType;
        this.time = time;
    }
}
