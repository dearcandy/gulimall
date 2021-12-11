package com.guigu.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.product.entity.AttrEntity;
import com.guigu.gulimall.product.vo.AttrGroupRelationVo;
import com.guigu.gulimall.product.vo.AttrVo;

import java.util.List;
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

    void saveAttrVo(AttrVo attrVO);

    PageUtils queryBaseAttrpage(Map<String, Object> params, Long catelogId, String type);

    /**
     * 根据分组ID找到属性分组关联的所有属性
     * @param attrGroupId
     * @return
     */
    List<AttrEntity> getRelationAttr(Long attrGroupId);

    /**
     * 删除属性分组和属性的关联关系
     * @param vos 属性ID和属性分组ID集合
     */
    void deleteAttrRelation(AttrGroupRelationVo[] vos);

    /**
     * 根据分组ID分页查询当前分组没有关联的属性
     * @param attrGroupId 当前分组ID
     * @param params 分页参数
     * @return 分页结果
     */
    PageUtils getNoRelationAttr(Long attrGroupId, Map<String, Object> params);
}

