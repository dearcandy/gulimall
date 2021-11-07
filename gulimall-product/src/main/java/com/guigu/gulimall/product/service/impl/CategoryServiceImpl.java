package com.guigu.gulimall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.product.dao.CategoryDao;
import com.guigu.gulimall.product.entity.CategoryEntity;
import com.guigu.gulimall.product.service.CategoryService;


/**
 * @author liuhangfei
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );
        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 查出所有分类
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);
        // 转换树形结构
        return categoryEntities.stream().filter(categoryEntity ->
             categoryEntity.getParentCid() == 0
        ).peek(menu-> menu.setChildren(getChildren(menu,categoryEntities)))
         .sorted(Comparator.comparingInt(CategoryEntity::getSort)).collect(Collectors.toList());
    }

    @Override
    public void removeMenuByIds(List<Long> catIds) {

        baseMapper.deleteBatchIds(catIds);
    }

    /**
     * 递归查找当前菜单的子菜单
     * @param root 当前节点
     * @param all 所有菜单
     * @return 菜单集合
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> all){
        return all.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid().equals(root.getCatId()))
                .peek(categoryEntity -> {
                    // 找到子菜单
                    categoryEntity.setChildren(getChildren(categoryEntity, all));
                })
                .sorted(Comparator.comparingInt(menu -> (menu.getSort() == null ? 0 : menu.getSort())))
                .collect(Collectors.toList());
    }

}