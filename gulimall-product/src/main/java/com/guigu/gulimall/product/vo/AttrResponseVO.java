package com.guigu.gulimall.product.vo;

import lombok.Data;

/**
 * @author: Trae.Liu
 * @create: 2021-12-05 01:43
 * @description:
 */
@Data
public class AttrResponseVO extends AttrVO{

    /**
     * 所属分类名字
     */
    private String catelogName;
    /**
     * 所属分组ID
     */
    private String groupName;
}
