package com.guigu.gulimall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;
import com.guigu.gulimall.coupon.dao.SkuFullReductionDao;
import com.guigu.gulimall.coupon.entity.MemberPriceEntity;
import com.guigu.gulimall.coupon.entity.SkuFullReductionEntity;
import com.guigu.gulimall.coupon.entity.SkuLadderEntity;
import com.guigu.gulimall.coupon.service.MemberPriceService;
import com.guigu.gulimall.coupon.service.SkuFullReductionService;
import com.guigu.gulimall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    SkuLadderService skuLadderService;

    @Autowired
    MemberPriceService memberPriceService;



    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }



}