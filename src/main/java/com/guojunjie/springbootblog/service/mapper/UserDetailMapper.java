package com.guojunjie.springbootblog.service.mapper;

import com.guojunjie.springbootblog.entity.User;
import com.guojunjie.springbootblog.entity.UserExtra;
import com.guojunjie.springbootblog.service.dto.UserDetail;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @Date： 2020/2/4 21:49
 * @author： guojunjie
 * TODO
 */
@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserDetailMapper {

    UserDetail userAndUserExtraToUserDetail(User user, UserExtra userExtra);
}
