package com.guigu.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.guigu.gulimall.product.entity.AttrEntity;
import com.guigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.guigu.gulimall.product.service.AttrService;
import com.guigu.gulimall.product.service.CategoryService;
import com.guigu.gulimall.product.vo.AttrGroupRelationVo;
import com.guigu.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.guigu.gulimall.product.entity.AttrGroupEntity;
import com.guigu.gulimall.product.service.AttrGroupService;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.R;

import javax.annotation.Resource;


/**
 * 属性分组
 *
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-11-06 14:16:01
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private AttrService attrService;
    @Resource
    private AttrAttrgroupRelationService attrAttrgroupRelationService;


    /**
     * 获取分类下属性分组信息以及所有属性信息
     * @param catlogId 分类ID
     * @return 分组信息以及所有属性信息
     */
    @GetMapping("/{catlogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catlogId") Long catlogId){
        List<AttrGroupWithAttrsVo> vos = attrGroupService.getAttrGroupWithAttrsByCatlogId(catlogId);
        return R.ok().put("data", vos);
    }

    /**
     * 根据分组ID找到属性分组关联的所有属性
     * @param attrGroupId
     * @return
     */
    @GetMapping("/{attrGroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrGroupId") Long attrGroupId){
        List<AttrEntity> attrEntityList = attrService.getRelationAttr(attrGroupId);
        return R.ok().put("data", attrEntityList);
    }

    // noattr/relation
    @GetMapping("/{attrGroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrGroupId") Long attrGroupId,
                            @RequestParam Map<String, Object> params){
        PageUtils page = attrService.getNoRelationAttr(attrGroupId,params);
        return R.ok().put("page", page);
    }

    /**
     * 新增关联关系
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> vos){
        attrAttrgroupRelationService.saveBatch(vos);
        return R.ok();
    }

    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId) {
        PageUtils page = attrGroupService.queryPage(params, catelogId);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        Long catelogId = attrGroup.getCatelogId();
        Long[] path = categoryService.findCategoryPath(catelogId);
        attrGroup.setCatelogPath(path);
        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup) {
        attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds) {
        attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }


    /**
     * 删除属性分组和属性的关联关系
     * @param vos
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R deleteAttrRelation(@RequestBody AttrGroupRelationVo[] vos){
        attrService.deleteAttrRelation(vos);
        return R.ok();
    }

}
