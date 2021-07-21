package com.guigu.gulimall.ware.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.ware.dao.WareOrderBillDetailDao;
import com.guigu.gulimall.ware.entity.WareOrderBillDetailEntity;
import com.guigu.gulimall.ware.service.WareOrderBillDetailService;


@Service("wareOrderBillDetailService")
public class WareOrderBillDetailServiceImpl extends ServiceImpl<WareOrderBillDetailDao, WareOrderBillDetailEntity> implements WareOrderBillDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareOrderBillDetailEntity> page = this.page(
                new Query<WareOrderBillDetailEntity>().getPage(params),
                new QueryWrapper<WareOrderBillDetailEntity>()
        );

        return new PageUtils(page);
    }

}