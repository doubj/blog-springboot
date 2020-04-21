package com.guojunjie.springbootblog.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Date： 2020/4/18 9:56
 * @author： guojunjie
 * 该DTO用于转换纵江湖前台ES搜索接口返回的集合记录，
 * 但由于需要从map转entity，所以mapstruct没用，只能用构造器
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogSearchDTO {
    private Integer blogId;
    private String blogContent;
    private String blogTitle;
    private String blogCategoryName;
}
