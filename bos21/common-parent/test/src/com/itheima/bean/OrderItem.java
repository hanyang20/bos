package com.itheima.bean;

public class OrderItem {
	/* `itemid` varchar(32) NOT NULL,
	 `count` int(11) DEFAULT NULL,
	 `subtotal` double DEFAULT NULL,
	 `pid` varchar(32) DEFAULT NULL,
	 `oid` varchar(32) DEFAULT NULL,*/
	private Integer itemid;
	private int count;
	private double subtotal;
	// 表示包含那个商品
	private Product product;
	// 表示属于那个订单
	private Orders orders;

	
	public Integer getItemid() {
		return itemid;
	}
	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Orders getOrders() {
		return orders;
	}
	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
}
