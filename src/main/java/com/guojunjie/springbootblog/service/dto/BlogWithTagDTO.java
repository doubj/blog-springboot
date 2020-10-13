package com.guojunjie.springbootblog.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date： 2020/4/21 14:41
 * @author： guojunjie
 * TODO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogWithTagDTO {
    private String tagName;
    private Integer blogCount;
}
