package com.guigu.gulimall.member.dao;

import com.guigu.gulimall.member.entity.UserLevelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员等级表
 * 
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-07-21 17:50:58
 */
@Mapper
public interface UserLevelDao extends BaseMapper<UserLevelEntity> {
	
}
