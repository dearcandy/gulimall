package com.guigu.gulimall.coupon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.to.MemberPrice;
import com.guigu.gulimall.common.to.SkuReductionTo;
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

import javax.annotation.Resource;
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

    // SKU的优惠满减信息 sms->
    // sms_sku_ladder/ sms_sku_full_reduction / sms_member_price /
    @Override
    public void saveSkuReduction(SkuReductionTo reductionTo) {
        // 保存满减打折，会员价
        SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
        skuLadderEntity.setSkuId(reductionTo.getSkuId());
        skuLadderEntity.setFullCount(reductionTo.getFullCount());
        skuLadderEntity.setDiscount(reductionTo.getDiscount());
        skuLadderEntity.setAddOther(reductionTo.getCountStatus());
        if (reductionTo.getFullCount() > 0){
            skuLadderService.save(skuLadderEntity);
        }

        // 满减信息保存
        SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
        BeanUtils.copyProperties(reductionTo, skuFullReductionEntity);
        if (reductionTo.getFullPrice().compareTo(BigDecimal.ZERO) > 0){
            this.save(skuFullReductionEntity);
        }

        // 会员价格保存
        List<MemberPrice> memberPrices = reductionTo.getMemberPrices();
        List<MemberPriceEntity> collect = memberPrices.stream().map(item -> {
            MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
            memberPriceEntity.setSkuId(reductionTo.getSkuId());
            memberPriceEntity.setMemberLevelId(item.getId());
            memberPriceEntity.setMemberLevelName(item.getName());
            memberPriceEntity.setMemberPrice(item.getPrice());
            memberPriceEntity.setAddOther(1);
            return memberPriceEntity;
        }).filter(item->{
            return item.getMemberPrice().compareTo(BigDecimal.ZERO) > 0;
        })
                .collect(Collectors.toList());
        memberPriceService.saveBatch(collect);
    }


}