package com.guigu.gulimall.common.to;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: Trae.Liu
 * @create: 2021-12-11 21:41
 * @description: SKU满减信息
 */
@Data
public class SkuReductionTo {

    private Long skuId;
    private int fullCount;
    private BigDecimal discount;
    private int countStatus;
    private BigDecimal fullPrice;
    private BigDecimal reducePrice;
    private int priceStatus;
    private List<MemberPrice> memberPrices;
}
