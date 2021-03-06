package com.guigu.gulimall.product.service.impl;

import com.guigu.gulimall.common.constant.ProductConstant;
import com.guigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.guigu.gulimall.product.dao.AttrGroupDao;
import com.guigu.gulimall.product.dao.CategoryDao;
import com.guigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.guigu.gulimall.product.entity.AttrGroupEntity;
import com.guigu.gulimall.product.entity.CategoryEntity;
import com.guigu.gulimall.product.vo.AttrGroupRelationVo;
import com.guigu.gulimall.product.vo.AttrRespVo;
import com.guigu.gulimall.product.vo.AttrVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import java.util.Arrays;
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
    public void saveAttrVo(AttrVo attrVO) {
        // ??????????????????
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVO, attrEntity);
        this.save(attrEntity);
        // ??????????????????
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
        List<AttrRespVo> responseVOList = records.stream().map((attrEntity -> {
            AttrRespVo attrResponseVO = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrResponseVO);
            // ??????????????????????????????
            if ("base".equalsIgnoreCase(type)){
                // ?????????????????????????????????
                AttrAttrgroupRelationEntity relationEntity = attrAttrgroupRelationDao.selectOne(
                        new QueryWrapper<AttrAttrgroupRelationEntity>()
                                .eq("attr_id", attrEntity.getAttrId()));
                if (relationEntity != null) {
                    // ??????????????????
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
                    attrResponseVO.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            // ??????????????????
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
     * ????????????ID?????????????????????????????????????????????
     * @param attrGroupId ??????ID
     * @return ????????????
     */
    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        // ????????????ID???????????????????????????ID??????
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", attrGroupId));
        List<Long> attrIds = entities.stream().map((attr) -> {
            return attr.getAttrId();
        }).collect(Collectors.toList());

        // ????????????ID????????????????????????
        List<AttrEntity> attrEntities = (List<AttrEntity>) this.listByIds(attrIds);
        return attrEntities;
    }

    /**
     * ??????????????????????????????????????????
     * @param vos ??????ID???????????????ID??????
     */
    @Override
    public void deleteAttrRelation(AttrGroupRelationVo[] vos) {
        // ??????????????????????????????
        List<AttrAttrgroupRelationEntity> relationEntityList = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        // ????????????????????????
        relationDao.deleteBatchRelation(relationEntityList);
    }


    /**
     * ????????????ID?????????????????????????????????????????????
     * @param attrGroupId ????????????ID
     * @param params ????????????
     * @return ????????????
     */
    @Override
    public PageUtils getNoRelationAttr(Long attrGroupId, Map<String, Object> params) {
        // ?????????????????????????????????????????????????????????
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
        Long catelogId = attrGroupEntity.getCatelogId();

        // ?????????????????????????????????????????????????????????
        // ??????????????????????????????
        List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectList(
                new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> groupIds = attrGroupEntities.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        // ???????????????????????????
        List<AttrAttrgroupRelationEntity> entities = relationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIds));
        List<Long> attrIds = entities.stream().map(item -> {
            return item.getAttrId();
        }).collect(Collectors.toList());


        // ???????????????????????????????????????????????????
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>()
                .eq("catelog_id", catelogId)
                .eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (!CollectionUtils.isEmpty(attrIds)){
            queryWrapper.notIn("attr_id", attrIds);
        }
        // ????????????
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