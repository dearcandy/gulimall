package com.guigu.gulimall.coupon.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.coupon.dao.CouponSpuDao;
import com.guigu.gulimall.coupon.entity.CouponSpuEntity;
import com.guigu.gulimall.coupon.service.CouponSpuService;


@Service("couponSpuService")
public class CouponSpuServiceImpl extends ServiceImpl<CouponSpuDao, CouponSpuEntity> implements CouponSpuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CouponSpuEntity> page = this.page(
                new Query<CouponSpuEntity>().getPage(params),
                new QueryWrapper<CouponSpuEntity>()
        );

        return new PageUtils(page);
    }

}