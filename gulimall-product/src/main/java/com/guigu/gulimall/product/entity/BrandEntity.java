package com.guigu.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.guigu.gulimall.common.valid.AddGroup;
import com.guigu.gulimall.common.valid.ListValue;
import com.guigu.gulimall.common.valid.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-11-06 14:16:01
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
//	@NotNull(message = "修改必须指定品牌ID", groups = {UpdateGroup.class})
//	@Null(message = "新增时不能指定ID", groups = {AddGroup.class})
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 */
//	@NotBlank(message = "品牌名必须提交！", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 品牌logo地址
	 */
//	@NotEmpty(message = "品牌logo地址不能为空")
//	@URL(message = "logo必须是一个合法的url地址！")
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
//	@ListValue(values = {0,1}, groups = {AddGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
//	@NotEmpty(message = "检索首字母不能为空")
//	@Pattern(regexp = "/^[a-zA-Z]$/", message = "检索首字母必须是另外一个字母")
	private String firstLetter;
	/**
	 * 排序
	 */
//	@NotNull
//	@Min(value = 0, message = "排序字段必须大于0")
	private Integer sort;

}
