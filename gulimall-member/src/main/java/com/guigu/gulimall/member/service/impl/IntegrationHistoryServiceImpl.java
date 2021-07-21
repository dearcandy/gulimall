package com.guigu.gulimall.member.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.common.utils.Query;

import com.guigu.gulimall.member.dao.IntegrationHistoryDao;
import com.guigu.gulimall.member.entity.IntegrationHistoryEntity;
import com.guigu.gulimall.member.service.IntegrationHistoryService;


@Service("integrationHistoryService")
public class IntegrationHistoryServiceImpl extends ServiceImpl<IntegrationHistoryDao, IntegrationHistoryEntity> implements IntegrationHistoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<IntegrationHistoryEntity> page = this.page(
                new Query<IntegrationHistoryEntity>().getPage(params),
                new QueryWrapper<IntegrationHistoryEntity>()
        );

        return new PageUtils(page);
    }

}