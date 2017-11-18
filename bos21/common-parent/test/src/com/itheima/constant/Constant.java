package com.itheima.constant;

public class Constant {
	/**
	 * 用户激活状态
	 * int state : 1 就是已经激活, 0就是未激活
	 * Ctrl+Shift+X/Y:变大/小写
	 */
	public static final int USER_ACTIVED = 1;
	public static final int USER_NOT_ACTIVE = 0;
	/**
	 * 商品是否是热门 Integer 1:热门    0:不热门
	 */
	public static final Integer PRODUCT_IS_HOT = 1;
	public static final Integer PRODUCT_ISNOT_HOT = 0;
	
	/**
	 *	 商品是否下架  1:下架	0:未下架  .  
	 * 
	 */
	public static final Integer PRODUCT_IS_OUT = 1;
	public static final Integer PRODUCT_IS_UP = 0;
	
	/**
	 * 分页查询商品一页显示的数量
	 */
	public static final int PRODUCT_PAGE_SIZE = 12;
	/**
	 * 订单的状态  0:未付款 	1:已付款 	2:已发货 	3.已完成   
	 */
	public static final Integer WEI_FU_KUAN = 0;
	public static final Integer YI_FU_KUAN = 1;
	public static final Integer YI_FA_HUO = 2;
	public static final Integer YI_WAN_CHENG = 3;
}
