package com.guigu.gulimall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @author: Trae.Liu
 * @create: 2021-12-12 17:43
 * @description: 合并采购单
 */
@Data
public class MergeVo {

    private Long purchaseId;
    private List<Long> items;
}
