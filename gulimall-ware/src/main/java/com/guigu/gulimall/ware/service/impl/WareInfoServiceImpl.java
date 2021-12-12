package com.guigu.gulimall.ware.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.ware.dao.WareInfoDao;
import com.guigu.gulimall.ware.entity.WareInfoEntity;
import com.guigu.gulimall.ware.service.WareInfoService;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<WareInfoEntity> wareInfoEntityQuery = new QueryWrapper<>();
        String key = (String) params.get("key");
        if (StringUtils.isNotEmpty(key)) {
            wareInfoEntityQuery.eq("id", key).or().like("name", key)
                    .or().like("address", key)
                    .or().like("areacode", key);
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),
                wareInfoEntityQuery
        );

        return new PageUtils(page);
    }

}