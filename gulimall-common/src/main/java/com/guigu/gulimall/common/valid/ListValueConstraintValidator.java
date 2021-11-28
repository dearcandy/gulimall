package com.guigu.gulimall.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Trae.Liu
 * @create: 2021-11-28 22:41
 * @description: 自定义校验类
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListValue, Integer> {

    private Set<Integer> set = new HashSet<>();

    /**
     * 初始化方法
     * @param constraintAnnotation 注解信息
     */
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] values = constraintAnnotation.values();
        for (int value :values) {
            set.add(value);
        }
    }

    /**
     * 校验结果判断
     * @param value 需要校验的值
     * @param constraintValidatorContext 上下文
     * @return 校验结果
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return set.contains(value);
    }
}
