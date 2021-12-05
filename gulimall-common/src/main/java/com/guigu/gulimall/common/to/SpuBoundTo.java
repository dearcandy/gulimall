package com.guigu.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: Trae.Liu
 * @create: 2021-12-06 00:29
 * @description: 积分
 */
@Data
public class SpuBoundTo {
    private Long spuId;
    private BigDecimal buyBounds;
    private BigDecimal growBounds;
}
