package com.guigu.gulimall.product.service.impl;

import com.guigu.gulimall.product.dao.SkuDao;
import com.guigu.gulimall.product.entity.SkuEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.product.service.SkuService;


@Service("skuService")
public class SkuServiceImpl extends ServiceImpl<SkuDao, SkuEntity> implements SkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuEntity> page = this.page(
                new Query<SkuEntity>().getPage(params),
                new QueryWrapper<SkuEntity>()
        );

        return new PageUtils(page);
    }

}