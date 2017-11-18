package com.itheima.dao.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.itheima.bean.OrderItem;
import com.itheima.bean.Orders;
import com.itheima.bean.Product;
import com.itheima.bean.User;
import com.itheima.dao.OrderDao;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.ConnectionManager;
import com.itheima.utils.HibernateUtils;

public class OrderDaoImpl implements OrderDao {
	//添加订单
	@Override
	public void addOrders(Orders orders) throws Exception {
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
             /* QueryRunner queryRunner=new QueryRunner();
              Connection conn = ConnectionManager.getConnectionByLocalThread();
              String sql="insert into orders values(?,?,?,?,?,?,?,?)";
              Object[] params={orders.getOid(),
            		  orders.getOrdertime(),
            		  orders.getTotal(),
            		  orders.getState(),
            		  orders.getAddress(),
            		  orders.getName(),
            		  orders.getTelephone(),
            		  orders.getUser().getUid()
            		  };
              queryRunner.update(conn, sql, params);*/
		Session session = HibernateUtils.getOpenCurrentSession();
		session.save(orders);
		
	}
	
	@Test
	public void demo1() {
		Orders orders = new Orders();
		Session session = HibernateUtils.getOpenCurrentSession();
		Transaction transaction = session.beginTransaction();
		User user = session.get(User.class, 4);
		System.out.println(user);
		orders.setUser(user);
		session.save(orders);
		transaction.commit();
	}
	
	@Test
	public void demo2() {
		Session session = HibernateUtils.getOpenCurrentSession();
		Transaction transaction = session.beginTransaction();
		User user = new User();
		session.save(user);
		transaction.commit();
	}
	
	 //添加订单的同时添加订单项
	@Override
	public void addOrderItem(OrderItem orderItem) throws Exception {
		/* `itemid` varchar(32) NOT NULL,
		 `count` int(11) DEFAULT NULL,
		 `subtotal` double DEFAULT NULL,
		 `pid` varchar(32) DEFAULT NULL,
		 `oid` varchar(32) DEFAULT NULL,*/ 
		/*QueryRunner queryRunner=new QueryRunner();
        Connection conn = ConnectionManager.getConnectionByLocalThread();
        String sql="insert into orderitem values(?,?,?,?,?)";
        Set<OrderItem> orderItems = orders.getOrderItems();
        for (OrderItem orderItem : orderItems) {
        	Object[] params={orderItem.getItemid(),
        			         orderItem.getCount(),
        			         orderItem.getSubtotal(),
        			         orderItem.getProduct().getPid(),
        			         orderItem.getOrders().getOid()
        	};
        	queryRunner.update(conn, sql, params);
		}*/
		Session session = HibernateUtils.getOpenCurrentSession();
			session.save(orderItem);
		
	}
	//查询订单总条数
	@Override
	public int findTotalCountByUid(User user) throws Exception {
    /* QueryRunner queryRunner=new QueryRunner(C3P0Utils.getDataSource());
     String sql="select count(*) from orders where uid=?";
     Long query = (Long) queryRunner.query(sql, new ScalarHandler(),user.getUid());*/
		Session session = HibernateUtils.getOpenSession();
		Transaction tx = session.beginTransaction();
		//---------------------------
		Criteria criteria = session.createCriteria(Orders.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("user.uid", user.getUid()));
		Long uniqueResult = (Long) criteria.uniqueResult();
		//---------------------------
		tx.commit();
		session.close();
		return uniqueResult.intValue();
	}
	//每页显示的订单条数的数据集合
	@Override
	public List<Orders> findALLByUid(User user, int a, int b) throws Exception {
		 QueryRunner queryRunner=new QueryRunner(C3P0Utils.getDataSource());
	     String sql="select * from orders where uid=? order by ordertime limit ?,?";
	     List<Orders> list = queryRunner.query(sql, new BeanListHandler<>(Orders.class),user.getUid(),a,b);
	   //private List<OrderItem> orderItems = new ArrayList<>();
	   //循环所有的订单，为每个订单项填充订单项集合信息
		//因为order里面没有private List<OrderItem> orderItems，所以要封装
	     if(list!=null){
	     for (Orders orders : list) {
	    	//获得订单的oid
			String oid = orders.getOid()+"";
			//查询该订单的所有的订单项---mapList封装的是多个订单项和该订单项中的商品的信息
			 sql = "select * from orderitem i,product p where i.pid=p.pid and oid=?";
			List<Map<String,Object>> mapList = queryRunner.query(sql, new MapListHandler(),oid);
			//将mapList转换成List<OrderItem> orderItems 
			for (Map<String, Object> map : mapList) {
				//从map中取出count subtotal 封装到OrderItem中
				OrderItem orderItem=new OrderItem();
				BeanUtils.populate(orderItem, map);
				//从map中取出pimage pname shop_price 封装到Product中
				Product product=new Product();
				BeanUtils.populate(product, map);
				//将product封装到OrderItem
				orderItem.setProduct(product);
				
				//将orderitem封装到order中的orderItemList中
				orders.getOrderItems().add(orderItem);
			}
			orders.setUser(user);
		}
	}
		return list;
	}
	//点击付款，跳到付款页面
	@Override
	public Orders findOrdersByOid(String oid) throws Exception {
		 QueryRunner queryRunner=new QueryRunner(C3P0Utils.getDataSource());
	     String sql="select * from orders where oid=?";
	     Orders orders = queryRunner.query(sql, new BeanHandler<>(Orders.class), oid);
	        sql="select * from orderitem i,product p where i.pid=p.pid and oid=?";
	        List<Map<String,Object>> list = queryRunner.query(sql, new MapListHandler(), oid);
	        for (Map<String, Object> map : list) {
				OrderItem orderItem=new OrderItem();
				BeanUtils.populate(orderItem, map);
				
				Product product=new Product();
				BeanUtils.populate(product, map);
				
				orderItem.setProduct(product);
				
				orders.getOrderItems().add(orderItem);
			}
		return orders;
	}
}