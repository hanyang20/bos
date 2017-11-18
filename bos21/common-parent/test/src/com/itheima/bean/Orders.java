package com.itheima.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Orders {
/**
 *  `oid` varchar(32) NOT NULL,
	 `ordertime` datetime DEFAULT NULL,
	 `total` double DEFAULT NULL,
	 `state` int(11) DEFAULT NULL,
	 `address` varchar(30) DEFAULT NULL,
     `name` varchar(20) DEFAULT NULL,
	 `telephone` varchar(20) DEFAULT NULL,
	 `uid` varchar(32) DEFAULT NULL,
 */
	 private Integer  oid;
	 private Date  ordertime;
	 private double  total;
	 private Integer  state;//订单状态 0:未付款	1:已付款	2:已发货	3.已完成
	
	 private String  address;
	 private String  name;
	 private String  telephone;
	 
	//表示当前订单属于那个用户
		private User user;
		
	//表示当前订单包含的订单项
	private Set<OrderItem> orderItems = new HashSet<>();


	
	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<OrderItem> getOrderItems() {
		return orderItems;
	}

	
	 
}
