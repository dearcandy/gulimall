package com.guigu.gulimall.product.dao;

import com.guigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-11-06 14:16:01
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    /**
     * 批量删除关联关系
     * @param entities 关联关系实体集合
     */
    void deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);
}
