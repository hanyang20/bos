package com.itheima.bean;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Product {
	/**
	 *   `pid` varchar(32) NOT NULL,
		  `pname` varchar(50) DEFAULT NULL,
		  `market_price` double DEFAULT NULL,
		  `shop_price` double DEFAULT NULL,
		  `pimage` varchar(200) DEFAULT NULL,
		  `pdate` date DEFAULT NULL,
		  `is_hot` int(11) DEFAULT NULL,
		  `pdesc` varchar(255) DEFAULT NULL,
		  `pflag` int(11) DEFAULT NULL,
		  `cid` varchar(32) DEFAULT NULL,
	 */
	
	private Integer pid;
	private String pname;
	private Double market_price;
	
	private Double shop_price;
	private String pimage;
	private Date pdate;
	
	private Integer is_hot;  //是否热门  1:热门    0:不热门
	private String pdesc;
	private Integer pflag;	//是否下架    1:下架	0:未下架
	
	//cid 在mysql是外键, 表达这个商品属于哪个类别.  在Java(面向对象)里面, 用对象来表示这个商品属于哪个类别
	private Category category;

	
    private Set<OrderItem> orderItems=new HashSet<>();


	

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	


	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Double getMarket_price() {
		return market_price;
	}

	public void setMarket_price(Double market_price) {
		this.market_price = market_price;
	}

	public Double getShop_price() {
		return shop_price;
	}

	public void setShop_price(Double shop_price) {
		this.shop_price = shop_price;
	}

	public String getPimage() {
		return pimage;
	}

	public void setPimage(String pimage) {
		this.pimage = pimage;
	}

	public Date getPdate() {
		return pdate;
	}

	public void setPdate(Date pdate) {
		this.pdate = pdate;
	}

	public Integer getIs_hot() {
		return is_hot;
	}

	public void setIs_hot(Integer is_hot) {
		this.is_hot = is_hot;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public Integer getPflag() {
		return pflag;
	}

	public void setPflag(Integer pflag) {
		this.pflag = pflag;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	

	
}
