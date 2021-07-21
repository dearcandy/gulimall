package com.guigu.gulimall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.ware.dao.WareOrderBillDao;
import com.guigu.gulimall.ware.entity.WareOrderBillEntity;
import com.guigu.gulimall.ware.service.WareOrderBillService;


@Service("wareOrderBillService")
public class WareOrderBillServiceImpl extends ServiceImpl<WareOrderBillDao, WareOrderBillEntity> implements WareOrderBillService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareOrderBillEntity> page = this.page(
                new Query<WareOrderBillEntity>().getPage(params),
                new QueryWrapper<WareOrderBillEntity>()
        );

        return new PageUtils(page);
    }

}