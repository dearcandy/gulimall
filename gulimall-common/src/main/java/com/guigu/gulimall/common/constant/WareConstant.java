package com.guigu.gulimall.common.constant;

/**
 * @author: Trae.Liu
 * @create: 2021-12-12 17:49
 * @description: 库存常量
 */
public class WareConstant {

    /**
     * 采购单状态枚举
     */
    public enum PurchaseStatusEnum{
        CREATED(0, "新建"), ASSIGNED(1, "已分配"),
        RECEIVE(2, "已领取"), FINISH(3, "已完成"),
        HAS_ERROR(4, "有异常");
        private int code;
        private String msg;

        PurchaseStatusEnum(int code, String msg){
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


    /**
     * 采购需求状态枚举
     */
    public enum PurchaseDetailStatusEnum{
        CREATED(0, "新建"), ASSIGNED(1, "已分配"),
        PURCHASING(2, "正在采购"), FINISH(3, "已完成"),
        HAS_ERROR(4, "采购失败");
        private int code;
        private String msg;

        PurchaseDetailStatusEnum(int code, String msg){
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
