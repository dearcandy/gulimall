package com.guigu.gulimall.product.feign;


import com.guigu.gulimall.common.to.SpuBoundTo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 会员服务
 * @author liuhangfei
 */
@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    @PostMapping("/coupon/spubounds/save")
    void saveSpuBounds(SpuBoundTo spuBoundTo);
}
