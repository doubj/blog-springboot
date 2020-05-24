package com.guojunjie.springbootblog.service.mapper;

import com.guojunjie.springbootblog.entity.Comment;
import com.guojunjie.springbootblog.service.dto.CommentItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @Date： 2020/5/5 12:20
 * @author： guojunjie
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentItemMapper extends BaseMapper<CommentItemDTO, Comment> {
}
