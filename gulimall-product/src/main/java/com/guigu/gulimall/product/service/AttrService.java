package com.guigu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.product.entity.AttrEntity;
import com.guigu.gulimall.product.vo.AttrVO;

import java.util.Map;

/**
 * 商品属性
 *
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-11-06 14:16:01
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttrVo(AttrVO attrVO);

    PageUtils queryBaseAttrpage(Map<String, Object> params, Long catelogId);
}

