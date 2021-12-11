package com.guigu.gulimall.product.feign;


import com.guigu.gulimall.common.to.SkuReductionTo;
import com.guigu.gulimall.common.to.SpuBoundTo;
import com.guigu.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 会员服务
 * @author liuhangfei
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    /**
     *
     * @param spuBoundTo
     * @return
     */
    @PostMapping("/coupon/spubounds/save")
    R saveSpuBounds(@RequestBody SpuBoundTo spuBoundTo);

    /**
     *
     * @param skuReductionTo
     * @return
     */
    @PostMapping("/coupon/skufullreduction/saveInfo")
    R saveSkuReduction(@RequestBody SkuReductionTo skuReductionTo);
}
