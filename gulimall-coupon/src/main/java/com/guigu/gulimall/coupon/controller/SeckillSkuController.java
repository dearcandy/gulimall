package com.guigu.gulimall.coupon.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guigu.gulimall.coupon.entity.SeckillSkuEntity;
import com.guigu.gulimall.coupon.service.SeckillSkuService;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.R;



/**
 * 秒杀活动商品关联
 *
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-07-21 18:13:32
 */
@RestController
@RequestMapping("coupon/seckillsku")
public class SeckillSkuController {
    @Autowired
    private SeckillSkuService seckillSkuService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = seckillSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		SeckillSkuEntity seckillSku = seckillSkuService.getById(id);

        return R.ok().put("seckillSku", seckillSku);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody SeckillSkuEntity seckillSku){
		seckillSkuService.save(seckillSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody SeckillSkuEntity seckillSku){
		seckillSkuService.updateById(seckillSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		seckillSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
