package com.guigu.gulimall.product.vo;

import lombok.Data;

/**
 * @author: Trae.Liu
 * @create: 2021-12-05 16:31
 * @description: 删除属性分组关联的属性请求
 */
@Data
public class AttrGroupRelationVo {

    /**
     * 属性ID
     */
    private Long attrId;
    /**
     * 属性分组ID
     */
    private Long attrGroupId;
}
