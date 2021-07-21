package com.guigu.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guigu.gulimall.common.utils.PageUtils;
import com.guigu.gulimall.ware.entity.WareEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-07-21 18:05:01
 */
public interface WareService extends IService<WareEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

