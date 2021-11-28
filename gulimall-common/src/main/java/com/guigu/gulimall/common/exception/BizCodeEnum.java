package com.guigu.gulimall.common.exception;

/**
 * @author: Trae.Liu
 * @create: 2021-11-28 22:01
 * @description: 状态码枚举
 */
public enum BizCodeEnum {

    UNKNOWN_EXCEPTION(10000, "系统未知异常"),
    VALID_EXCEPTION(10001, "参数格式校验失败");

    private Integer code;
    private String message;

    BizCodeEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return this.code;
    }

    public String getMessage(){
        return  this.message;
    }


}
