package com.guojunjie.springbootblog.service.mapper;

import java.util.List;

/**
 * @Date： 2020/2/4 14:14
 * @author： guojunjie
 * TODO mapper接口定义
 */
public interface BaseMapper<D, E> {
    /**
     * DTO转Entity
     * @param dto
     * @return
     */
    E toEntity(D dto);

    /**
     * Entity转DTO
     * @param entity
     * @return
     */
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     * @param dtoList
     * @return
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * Entity集合转DTO集合
     * @param entityList
     * @return
     */
    List <D> toDto(List<E> entityList);
}
