package com.itheima.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itheima.bean.OrderItem;
import com.itheima.bean.Orders;
import com.itheima.bean.PageBean;
import com.itheima.bean.User;
import com.itheima.dao.OrderDao;
import com.itheima.dao.impl.OrderDaoImpl;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.ConnectionManager;
import com.itheima.utils.HibernateUtils;

public class OrderServiceImpl implements OrderService {
	static OrderDao dao=(OrderDao) BeanFactory.getBean("OrderDao");
	//提交订单
	@Override
	public void submitOrder(Orders orders) {
		Transaction tx=null;
		try {
			//订单和订单项是同时完成，所以用事务
			/*conn = ConnectionManager.getConnectionByLocalThread();
			conn.setAutoCommit(false);*/
			Session session = HibernateUtils.getOpenCurrentSession();
			tx = session.beginTransaction();
			//OrderDao dao=new OrderDaoImpl();
			dao.addOrders(orders);
			Set<OrderItem> orderItems = orders.getOrderItems();
			for (OrderItem orderItem : orderItems) {
				dao.addOrderItem(orderItem);
			}
			
		} catch (Exception e) {
				e.printStackTrace();
				tx.rollback();
		}
		tx.commit();
		
	}
	//分页显示订单
	@Override
	public PageBean<Orders> fidByUid(User user, int currentPage, int currentCount) throws Exception {
		PageBean<Orders> pageBean=new PageBean<>();
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		
		OrderDao orderDao=(OrderDao) BeanFactory.getBean("OrderDao");
		int totalCount=orderDao.findTotalCountByUid(user);
		pageBean.setTotalCount(totalCount);
		
		int totalPage=0;
		if(totalCount % currentCount==0){
			totalPage=totalCount / currentCount;
		}else{
			totalPage=totalCount / currentCount+1;
		}
		pageBean.setTotalPage(totalPage);
		
		int b=currentCount;
		int a=(currentPage-1)*currentCount;
		List<Orders> list=orderDao.findALLByUid(user,a,b);
		pageBean.setList(list);
		
		return pageBean;
	}
	//点击付款，跳到付款页面
	@Override
	public Orders findOrdersByOid(String oid) throws Exception {
		OrderDao orderDao=(OrderDao) BeanFactory.getBean("OrderDao");
		Orders orders = orderDao.findOrdersByOid(oid);
		return orders;
	}

}
