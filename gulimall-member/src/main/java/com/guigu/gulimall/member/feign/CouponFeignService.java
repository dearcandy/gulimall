package com.guigu.gulimall.member.feign;

import com.guigu.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName CouponFeignService
 * @Description TODO
 * @Author DearCandy
 * @Date 2021/7/24 19:27
 * @Version 1.0.0
 **/
@FeignClient("gulimall-coupon")
public interface CouponFeignService {

    /**
     * 得到一个R对象
     * @return R 统一响应
     */
    @RequestMapping("/coupon/coupon/member/list")
    R memberCoupons();
}
