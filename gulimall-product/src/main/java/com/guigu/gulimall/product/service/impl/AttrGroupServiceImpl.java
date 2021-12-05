package com.guigu.gulimall.product.service.impl;

import com.guigu.gulimall.product.entity.AttrEntity;
import com.guigu.gulimall.product.service.AttrService;
import com.guigu.gulimall.product.vo.AttrGroupWithAttrsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.product.dao.AttrGroupDao;
import com.guigu.gulimall.product.entity.AttrGroupEntity;
import com.guigu.gulimall.product.service.AttrGroupService;

import javax.annotation.Resource;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Resource
    private AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        String key = (String) params.get("key");
        QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>();
        if (StringUtils.isNotEmpty(key)){
            wrapper.and(obj->{
                obj.eq("attr_group_id", key).or().like("attr_group_name", key);
            });
        }

        // 不选中三级分类 查询所有
        if (catelogId == 0){
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                    new QueryWrapper<AttrGroupEntity>());
            return new PageUtils(page);
        }else{
            wrapper.eq("catelog_id", catelogId);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                   wrapper);
            return new PageUtils(page);
        }
    }

    @Override
    public List<AttrGroupWithAttrsVO> getAttrGroupWithAttrsByCatlogId(Long catlogId) {
        // 获取当前分类下所有属性分组信息
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catlogId));

        // 获取每个属性分组下的所有属性
        List<AttrGroupWithAttrsVO> vos = attrGroupEntities.stream().map(item -> {
            AttrGroupWithAttrsVO attrGroupWithAttrsVO = new AttrGroupWithAttrsVO();
            BeanUtils.copyProperties(item, attrGroupWithAttrsVO);
            List<AttrEntity> attrs = attrService.getRelationAttr(attrGroupWithAttrsVO.getAttrGroupId());
            attrGroupWithAttrsVO.setAttrs(attrs);
            return attrGroupWithAttrsVO;
        }).collect(Collectors.toList());
        return vos;
    }

}