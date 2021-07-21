package com.guigu.gulimall.product.dao;

import com.guigu.gulimall.product.entity.CommentEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品评价
 * 
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-07-21 14:44:23
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {
	
}
