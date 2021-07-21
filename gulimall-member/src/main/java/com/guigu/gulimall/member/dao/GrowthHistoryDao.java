package com.guigu.gulimall.member.dao;

import com.guigu.gulimall.member.entity.GrowthHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 成长积分记录表
 * 
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-07-21 17:50:58
 */
@Mapper
public interface GrowthHistoryDao extends BaseMapper<GrowthHistoryEntity> {
	
}
