package com.guigu.gulimall.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.guigu.gulimall.product.entity.AttrEntity;
import lombok.Data;

import java.util.List;

/**
 * @author: Trae.Liu
 * @create: 2021-12-05 19:19
 * @description: 属性分组基本信息和对应属性信息
 */
@Data
public class AttrGroupWithAttrsVO {


    /**
     * 分组id
     */
    private Long attrGroupId;
    /**
     * 组名
     */
    private String attrGroupName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String descript;
    /**
     * 组图标
     */
    private String icon;
    /**
     * 所属分类id
     */
    private Long catelogId;
    /**
     * 属性列表
     */
    private List<AttrEntity> attrs;
}
