package com.itheima.dao;

import java.util.List;

import com.itheima.bean.OrderItem;
import com.itheima.bean.Orders;
import com.itheima.bean.User;

public interface OrderDao {
   //添加订单
	void addOrders(Orders orders) throws Exception;
  //添加订单的同时添加订单项
	void addOrderItem(OrderItem orderItem) throws Exception;
	//我的订单分页显示
	int findTotalCountByUid(User user) throws Exception;
	List<Orders> findALLByUid(User user, int a, int b) throws Exception;
	//点击付款，跳到付款页面
	Orders findOrdersByOid(String oid) throws Exception;
	

}
