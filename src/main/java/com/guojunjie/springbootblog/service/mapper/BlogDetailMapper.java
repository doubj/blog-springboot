package com.guojunjie.springbootblog.service.mapper;

import com.guojunjie.springbootblog.entity.Blog;
import com.guojunjie.springbootblog.service.dto.BlogDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @Date： 2020/2/4 14:27
 * @author： guojunjie
 * TODO
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BlogDetailMapper extends BaseMapper<BlogDetailDTO, Blog>{

}
