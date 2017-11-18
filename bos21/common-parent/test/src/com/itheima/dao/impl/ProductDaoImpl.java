package com.itheima.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.itheima.bean.Product;
import com.itheima.constant.Constant;
import com.itheima.dao.ProductDao;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.HibernateUtils;

public class ProductDaoImpl implements ProductDao {
	//查询热门商品
	@Override
	public List<Product> findHotProductList() throws Exception {
		/*QueryRunner queryRunner=new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from product where is_hot=? limit ?,?";
		return queryRunner.query(sql, new BeanListHandler<>(Product.class),0,0,9);*/
		Session session = HibernateUtils.getOpenSession();
		Transaction tx = session.beginTransaction();
		//------------------------------------
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq("is_hot", 1));
		criteria.setFirstResult(0);
		criteria.setMaxResults(9);
		List<Product> list = criteria.list();
		//------------------------------------
		tx.commit();
		session.close();
		return list;
	}
	//查询最新商品
	@Override
	public List<Product> findPdateProductList() throws Exception {
		/*QueryRunner queryRunner=new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from product order by pdate desc limit ?,?";
		return queryRunner.query(sql, new BeanListHandler<>(Product.class),0,9);*/
		Session session = HibernateUtils.getOpenSession();
		Transaction tx = session.beginTransaction();
		//------------------------------------
		Criteria criteria = session.createCriteria(Product.class);
		criteria.addOrder(Order.desc("pdate"));
		criteria.setFirstResult(0);
		criteria.setMaxResults(9);
		List<Product> list = criteria.list();
		//------------------------------------
		tx.commit();
		session.close();
		return list;
		
	}
	//点击商品并展示
	@Override
	public Product showProduct(String pid) throws Exception {
		/*QueryRunner queryRunner=new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from product where pid=?";
		return queryRunner.query(sql, new BeanHandler<>(Product.class),pid);*/
		Session session = HibernateUtils.getOpenSession();
		Transaction tx = session.beginTransaction();
		//------------------------------------
	     Product product = session.get(Product.class, Integer.parseInt(pid));
		//------------------------------------
		tx.commit();
		session.close();
		return product;
	}
	// 分页查询： 返回的每个类别商品的总条数
	@Override
	public int findtotalCount(String cid) throws Exception {
		/*QueryRunner queryRunner=new QueryRunner(C3P0Utils.getDataSource());
		String sql="select count(*) from product where cid=?";
		Long query = (Long) queryRunner.query(sql, new ScalarHandler(),cid);
		return query.intValue();*/
		Session session = HibernateUtils.getOpenSession();
		Transaction tx = session.beginTransaction();
		//------------------------------------
		/*Criteria criteria = session.createCriteria(Product.class);
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("cid", Integer.parseInt(cid)));
		Number number=(Number) criteria.uniqueResult();*/
		String hql="select count(*) from Product where cid = ?";
		Query query = session.createQuery(hql);
		query.setParameter(0, Integer.parseInt(cid));
		Number number = (Number) query.uniqueResult();
		//------------------------------------
		tx.commit();
		session.close();
		return number.intValue();
	}
	//分页查询： 返回的是每页显示商品个数的集合
	@Override
	public List<Product> findCurrentPageShowProductCount(String cid, int a, int b) throws SQLException {
		/*QueryRunner queryRunner=new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from product where cid=? limit ?,?";
		List<Product> list = queryRunner.query(sql, new BeanListHandler<>(Product.class),cid,a,b);
		return list;*/
		Session session = HibernateUtils.getOpenSession();
		Transaction tx = session.beginTransaction();
		//------------------------------------
		Criteria criteria = session.createCriteria(Product.class);
		criteria.add(Restrictions.eq("category.cid", Integer.parseInt(cid)));
		criteria.setFirstResult(a);
		criteria.setMaxResults(b);
		List<Product> list = criteria.list();
		//------------------------------------
		tx.commit();
		session.close();
		return list;
	}

}
