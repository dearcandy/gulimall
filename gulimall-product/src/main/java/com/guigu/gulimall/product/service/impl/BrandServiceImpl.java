package com.guigu.gulimall.product.service.impl;

import com.guigu.gulimall.product.dao.CategoryBrandRelationDao;
import com.guigu.gulimall.product.service.CategoryBrandRelationService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.product.dao.BrandDao;
import com.guigu.gulimall.product.entity.BrandEntity;
import com.guigu.gulimall.product.service.BrandService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("brandService")
public class BrandServiceImpl extends ServiceImpl<BrandDao, BrandEntity> implements BrandService {

    @Resource
    private CategoryBrandRelationService categoryBrandRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 获取key
        String key = (String) params.get("key");
        QueryWrapper<BrandEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(key)){
            wrapper.eq("brand_id", key).or().like("name", key);
        }

        IPage<BrandEntity> page = this.page(new Query<BrandEntity>().getPage(params), wrapper);

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateDetail(BrandEntity brand) {
        // 保证冗余字段数据一致
        this.updateById(brand);
        if (StringUtils.isNotEmpty(brand.getName())){
            // 同步更新关联表信息
            categoryBrandRelationService.updateBrand(brand.getBrandId(), brand.getName());
            // TODO 更新其他关联
        }
    }

}