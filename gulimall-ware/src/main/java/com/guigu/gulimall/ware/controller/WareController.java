package com.guigu.gulimall.ware.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guigu.gulimall.ware.entity.WareEntity;
import com.guigu.gulimall.ware.service.WareService;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.R;



/**
 * 仓库信息
 *
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-07-21 18:05:01
 */
@RestController
@RequestMapping("ware/ware")
public class WareController {
    @Autowired
    private WareService wareService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		WareEntity ware = wareService.getById(id);

        return R.ok().put("ware", ware);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody WareEntity ware){
		wareService.save(ware);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody WareEntity ware){
		wareService.updateById(ware);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		wareService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
