package com.guigu.gulimall.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.member.dao.UserCollectSkuDao;
import com.guigu.gulimall.member.entity.UserCollectSkuEntity;
import com.guigu.gulimall.member.service.UserCollectSkuService;


@Service("userCollectSkuService")
public class UserCollectSkuServiceImpl extends ServiceImpl<UserCollectSkuDao, UserCollectSkuEntity> implements UserCollectSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserCollectSkuEntity> page = this.page(
                new Query<UserCollectSkuEntity>().getPage(params),
                new QueryWrapper<UserCollectSkuEntity>()
        );

        return new PageUtils(page);
    }

}