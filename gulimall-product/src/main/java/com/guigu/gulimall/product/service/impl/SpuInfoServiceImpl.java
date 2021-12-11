package com.guigu.gulimall.product.service.impl;

import com.guigu.gulimall.common.to.SkuReductionTo;
import com.guigu.gulimall.common.to.SpuBoundTo;
import com.guigu.gulimall.common.utils.R;
import com.guigu.gulimall.product.entity.*;
import com.guigu.gulimall.product.feign.CouponFeignService;
import com.guigu.gulimall.product.service.*;
import com.guigu.gulimall.product.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;
import com.guigu.gulimall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Resource
    private SpuInfoDescService spuInfoDescService;
    @Resource
    private SpuImagesService spuImagesService;
    @Resource
    private AttrService attrService;
    @Resource
    private ProductAttrValueService productAttrValueService;
    @Resource
    private SkuInfoService skuInfoService;
    @Resource
    private SkuImagesService skuImagesService;
    @Resource
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Resource
    private CouponFeignService couponFeignService;
    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveSpuInfo(SpuSaveVo vo) {
        // SPU基本信息保存 pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(vo,spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);

        // SPU描述信息保存 pms_spu_info_desc
        List<String> description = vo.getDecript();
        SpuInfoDescEntity descEntity = new SpuInfoDescEntity();
        descEntity.setSpuId(spuInfoEntity.getId());
        descEntity.setDecript(String.join(",", description));
        spuInfoDescService.saveSpuInfoDesc(descEntity);

        // SPU图片集保存 pms_spu_images
        List<String> images = vo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(),images);

        // SPU的规格参数保存 pms_product_attr_value
        List<BaseAttrs> baseAttrs = vo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(item -> {
            ProductAttrValueEntity attrValueEntity = new ProductAttrValueEntity();
            attrValueEntity.setAttrId(item.getAttrId());
            // 查询属性名
            AttrEntity attrEntity = attrService.getById(item.getAttrId());
            attrValueEntity.setAttrName(attrEntity.getAttrName());
            attrValueEntity.setAttrValue(item.getAttrValues());
            attrValueEntity.setQuickShow(item.getShowDesc());
            attrValueEntity.setSpuId(spuInfoEntity.getId());
            return attrValueEntity;
        }).collect(Collectors.toList());
        productAttrValueService.saveProductAttr(collect);

        // SPU的积分信息  sms->sms_spu_bounds
        Bounds bounds = vo.getBounds();
        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(bounds, spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        R saveSpuBounds = couponFeignService.saveSpuBounds(spuBoundTo);
        if (saveSpuBounds.getCode() != 0){
            log.error("远程保存SPU积分信息失败");
        }

        // 保存SPU的所有SKU信息
        List<Skus> skus = vo.getSkus();
        if (!CollectionUtils.isEmpty(skus)){
            skus.forEach(item->{
                // 获取默认图片
                String defaultImg = "";
                for (Images image :item.getImages()) {
                    if (image.getDefaultImg() == 1){
                        defaultImg = image.getImgUrl();
                    }
                }

                // SKU的基本信息 pms_sku_info
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(item, skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0L);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImg);
                skuInfoService.saveSkuInfo(skuInfoEntity);

                // SKU的图片信息 pms_sku_images
                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> imagesEntities = item.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(entity->{
                    // 返回true就是需要 false剔除
                    return StringUtils.isNotEmpty(entity.getImgUrl());
                }).collect(Collectors.toList());
                skuImagesService.saveBatch(imagesEntities);

                // SKU的销售属性信息 pms_sku_sale_attr_value
                try{
                    List<Attr> attr = item.getAttr();
                    List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(attrEntity -> {
                        SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                        BeanUtils.copyProperties(attrEntity, skuSaleAttrValueEntity);
                        skuSaleAttrValueEntity.setSkuId(skuId);
                        return skuSaleAttrValueEntity;
                    }).collect(Collectors.toList());
                    System.out.println(skuSaleAttrValueEntities);
                    skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);


                    // SKU的优惠满减信息 sms->
                    // sms_sku_ladder/ sms_sku_full_reduction / sms_member_price /
                    SkuReductionTo skuReductionTo = new SkuReductionTo();
                    BeanUtils.copyProperties(item,skuReductionTo);
                    skuReductionTo.setSkuId(skuId);
                    if (skuReductionTo.getFullCount() > 0  || skuReductionTo.getFullPrice().compareTo(BigDecimal.ZERO) > 0){
                        R result = couponFeignService.saveSkuReduction(skuReductionTo);
                        if (result.getCode() != 0){
                            log.error("远程保护SKU优惠信息失败！");
                        }
                    }

                }catch (Exception exception){
                    log.error("SKU的销售属性信息保存出错", exception);
                }
            });
        }




    }

    /**
     * 保存SPU基本信息
     * @param spuInfoEntity SPU基本信息实体
     */
    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        int insert = this.baseMapper.insert(spuInfoEntity);
        System.out.println(insert);

    }


    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {

        QueryWrapper<SpuInfoEntity> wrapper = new QueryWrapper<>();

        String key = (String) params.get("key");
        if(!org.springframework.util.StringUtils.isEmpty(key)){
            wrapper.and((w)->{
                w.eq("id",key).or().like("spu_name",key);
            });
        }
        // status=1 and (id=1 or spu_name like xxx)
        String status = (String) params.get("status");
        if(!org.springframework.util.StringUtils.isEmpty(status)){
            wrapper.eq("publish_status",status);
        }

        String brandId = (String) params.get("brandId");
        if(!org.springframework.util.StringUtils.isEmpty(brandId)&&!"0".equalsIgnoreCase(brandId)){
            wrapper.eq("brand_id",brandId);
        }

        String catelogId = (String) params.get("catelogId");
        if(!org.springframework.util.StringUtils.isEmpty(catelogId)&&!"0".equalsIgnoreCase(catelogId)){
            wrapper.eq("catalog_id",catelogId);
        }

        /**
         * status: 2
         * key:
         * brandId: 9
         * catelogId: 225
         */

        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }


}