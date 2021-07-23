package com.guigu.gulimall.member.fegin;

import com.guigu.gulimall.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @ClassName CouponService
 * @Description gulimall-coupon FeignClient Interface
 * @Author DearCandy
 * @Date 2021/7/23 13:24
 * @Version 1.0.0
 **/
@FeignClient("gulimall-coupon")
public interface CouponService {


    @RequestMapping("/coupon/coupon/member/list")
    R list(@RequestParam Map<String, Object> params);

}
