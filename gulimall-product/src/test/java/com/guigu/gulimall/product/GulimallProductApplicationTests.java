package com.guigu.gulimall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.guigu.gulimall.product.entity.BrandEntity;
import com.guigu.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    public void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();
////        brandEntity.setBrandId(1l);
////        brandEntity.setName("华为");
////        brandService.updateById(brandEntity);
//
//        brandEntity.setName("华为");
//        brandService.save(brandEntity);
//        System.out.println("保存成功");

        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("name", "华为"));
        System.out.println(list);

    }

}
