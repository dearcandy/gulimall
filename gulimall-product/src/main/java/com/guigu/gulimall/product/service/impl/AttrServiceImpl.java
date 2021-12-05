package com.guigu.gulimall.product.service.impl;

import com.guigu.gulimall.common.constant.ProductConstant;
import com.guigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.guigu.gulimall.product.dao.AttrGroupDao;
import com.guigu.gulimall.product.dao.CategoryDao;
import com.guigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.guigu.gulimall.product.entity.AttrGroupEntity;
import com.guigu.gulimall.product.entity.CategoryEntity;
import com.guigu.gulimall.product.vo.AttrGroupRelationVo;
import com.guigu.gulimall.product.vo.AttrResponseVO;
import com.guigu.gulimall.product.vo.AttrVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.product.dao.AttrDao;
import com.guigu.gulimall.product.entity.AttrEntity;
import com.guigu.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {


    @Resource
    private AttrAttrgroupRelationDao relationDao;
    @Resource
    private AttrGroupDao attrGroupDao;
    @Resource
    private CategoryDao categoryDao;
    @Resource
    private AttrAttrgroupRelationDao attrAttrgroupRelationDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAttrVo(AttrVO attrVO) {
        // 保存基本信息
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVO, attrEntity);
        this.save(attrEntity);
        // 保存关联信息
        if(attrVO.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attrVO.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            attrAttrgroupRelationDao.insert(relationEntity);
        }
    }

    @Override
    public PageUtils queryBaseAttrpage(Map<String, Object> params, Long catelogId, String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("attr_type", "base".equalsIgnoreCase(type)?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                        :ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        if (catelogId != 0){
            queryWrapper.eq("catelog_id", catelogId);
        }

        String key = (String) params.get("key");
        if (StringUtils.isNotEmpty(key)){
            queryWrapper.and((wrapper)->{
                wrapper.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrResponseVO> responseVOList = records.stream().map((attrEntity -> {
            AttrResponseVO attrResponseVO = new AttrResponseVO();
            BeanUtils.copyProperties(attrEntity, attrResponseVO);
            // 设置分类和分组的名字
            if ("base".equalsIgnoreCase(type)){
                // 获取属性和属性分组关系
                AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(
                        new QueryWrapper<AttrAttrgroupRelationEntity>()
                                .eq("attr_id", attrEntity.getAttrId()));
                if (relationEntity != null) {
                    // 获取分组信息
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    attrResponseVO.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            // 获取分类信息
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrResponseVO.setCatelogName(categoryEntity.getName());
            }

            return attrResponseVO;
        })).collect(Collectors.toList());

        pageUtils.setList(responseVOList);
        return pageUtils;
    }

    /**
     * 根据分组ID找到属性分组关联的所有基本属性
     * @param attrGroupId 分组ID
     * @return 属性集合
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        // 根据分组ID在关系表中获取属性ID集合
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));
        List<Long> attrIds = entities.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        // 根据属性ID集合获取对应实体
        List<AttrEntity> attrEntities = (List<AttrEntity>) this.listByIds(attrIds);
        return attrEntities;
    }

    /**
     * 删除属性分组和属性的关联关系
     * @param vos 属性ID和属性分组ID集合
     */
    @Override
    public void deleteAttrRelation(AttrGroupRelationVo[] vos) {
        // 构造关联关系实体集合
        List<AttrAttrgroupRelationEntity> relationEntityList = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        // 批量删除关联关系
        relationDao.deleteBatchRelation(relationEntityList);
    }


    /**
     * 根据分组ID分页查询当前分组没有关联的属性
     * @param attrGroupId 当前分组ID
     * @param params 分页参数
     * @return 分页结果
     */
    @Override
    public PageUtils getNoRelationAttr(Long attrGroupId, Map<String, Object> params) {
        // 当前分组只能关联自己所属的分类里的属性
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
        Long catelogId = attrGroupEntity.getCatelogId();

        // 当前分组只能关联其他分组还没引用的属性
        // 当前分类下的其他分组
        List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectList(
                new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> groupIds = attrGroupEntities.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        // 这些分组关联的属性
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIds));
        List<Long> attrIds = entities.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());


        // 从当前分类的所有属性中移除这些属性
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId)
                .eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (!CollectionUtils.isEmpty(attrIds)){
            queryWrapper.notIn("attr_id", attrIds);
        }
        // 模糊匹配
        String key = (String) params.get("key");
        if (StringUtils.isNotEmpty(key)){
            queryWrapper.and((w)->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), queryWrapper);
        PageUtils pageUtils = new PageUtils(page);

        return pageUtils;
    }

}