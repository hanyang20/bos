package com.itheima.service;

import java.util.List;

import com.itheima.bean.Orders;
import com.itheima.bean.PageBean;
import com.itheima.bean.User;

public interface OrderService {

	void submitOrder(Orders orders) throws Exception;
   //我的订单
	PageBean<Orders> fidByUid(User user, int currentPage, int currentCount) throws Exception;
	//点击付款，跳到确认订单页面
	Orders findOrdersByOid(String oid) throws Exception;

}
