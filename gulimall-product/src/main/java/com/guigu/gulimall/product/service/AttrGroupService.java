package com.guigu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.product.entity.AttrGroupEntity;
import com.guigu.gulimall.product.vo.AttrGroupWithAttrsVo;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-11-06 14:16:01
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    /**
     * 分页查询属性组信息
     * @param params 分页参数
     * @return 属性组
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据分类ID分页查询属性组信息
     * @param params 分页参数
     * @param catelogId 分类ID
     * @return 属性组
     */
    PageUtils queryPage(Map<String, Object> params, Long catelogId);

    /**
     * 获取分类下属性分组信息以及所有属性信息
     * @param catlogId 分类ID
     * @return 分类下属性分组信息以及所有属性信息
     */
    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatlogId(Long catlogId);
}

