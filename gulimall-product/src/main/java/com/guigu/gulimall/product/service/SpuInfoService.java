package com.guigu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.product.entity.SpuInfoEntity;
import com.guigu.gulimall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-11-06 14:16:00
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 商品SPU信息保存
     * @param vo
     */
    void saveSpuInfo(SpuSaveVo vo);

    /**
     * 保存SPU基本信息
     * @param spuInfoEntity
     */
    void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity);


    PageUtils queryPageByCondition(Map<String, Object> params);
}

