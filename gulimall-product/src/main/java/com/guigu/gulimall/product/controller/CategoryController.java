package com.guigu.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.guigu.gulimall.product.entity.CategoryEntity;
import com.guigu.gulimall.product.service.CategoryService;
import com.guigu.gulimall.common.utils.R;
import javax.annotation.Resource;


/**
 * 商品三级分类
 * @author dear_candy
 * @date 2021-11-06 14:16:00
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    /**
     * 查询所有分类-树形结构
     * @return R
     */
    @RequestMapping("/list/tree")
    public R list(){
        List<CategoryEntity> entityList = categoryService.listWithTree();

        return R.ok().put("data", entityList);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){

        // 检查当前删除的菜单是否被引用
		categoryService.removeByIds(Arrays.asList(catIds));
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
