package com.guigu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.product.entity.SpuEntity;

import java.util.Map;

/**
 * spu信息
 *
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-07-21 14:44:23
 */
public interface SpuService extends IService<SpuEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

