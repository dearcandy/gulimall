package com.guigu.gulimall.common.constant;

/**
 * @author: Trae.Liu
 * @create: 2021-12-05 15:34
 * @description: 商品相关常量
 */
public class ProductConstant {

    public enum AttrEnum{
        ATTR_TYPE_BASE(1, "基本属性"), ATTR_TYPE_SALE(2, "销售属性");
        private int code;
        private String msg;

        AttrEnum(int code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode(){
            return this.code;
        }

        public String getMsg(){
            return this.msg;
        }
    }
}
