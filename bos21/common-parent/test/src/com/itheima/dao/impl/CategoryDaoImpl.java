package com.itheima.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itheima.bean.Category;
import com.itheima.dao.CategoryDao;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.HibernateUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> findAllCategory() throws Exception {
		/*QueryRunner queryRunner=new QueryRunner(C3P0Utils.getDataSource());
		String sql="select * from category";
		return queryRunner.query(sql, new BeanListHandler<>(Category.class));*/
		Session session = HibernateUtils.getOpenSession();
		Transaction tx = session.beginTransaction();
		Criteria criteria = session.createCriteria(Category.class);
		List<Category> list = criteria.list();
		tx.commit();
		session.close();
		return list;
	}

}
