package com.guigu.gulimall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 关注商品表
 * 
 * @author dear_candy
 * @email dearcandy@gmail.com
 * @date 2021-07-21 17:50:58
 */
@Data
@TableName("ums_user_collect_sku")
public class UserCollectSkuEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * sku_id
	 */
	private Long skuId;
	/**
	 * sku标题
	 */
	private String skuTitle;
	/**
	 * sku默认图片
	 */
	private String skuImage;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
