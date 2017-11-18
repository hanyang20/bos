package com.itheima.dao.impl;

import java.io.Serializable;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.itheima.bean.User;
import com.itheima.dao.UserDao;
import com.itheima.utils.C3P0Utils;
import com.itheima.utils.HibernateUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public int save(User user) throws Exception {
		QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
		/**
		 *  * 	`uid` varchar(32) NOT NULL,  
	 `username` varchar(20) DEFAULT NULL,
	 `password` varchar(20) DEFAULT NULL,
     `name` varchar(20) DEFAULT NULL,
	 `email` varchar(30) DEFAULT NULL,
	 `telephone` varchar(20) DEFAULT NULL,
	 `birthday` date DEFAULT NULL,
	 `sex` varchar(10) DEFAULT NULL,
	 `state` int(11) DEFAULT NULL,  
	 `code` varchar(64) DEFAULT NULL,
		 */
		/*String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		
		Object[] params= {user.getUid(),user.getUsername(),user.getPassword(),
		user.getName(),user.getEmail(),user.getTelephone(), user.getBirthday(),user.getSex(),
		user.getState(),user.getCode()
		};
		queryRunner.update(sql, params);*/
		Session session = HibernateUtils.getOpenSession();
		Transaction tx = session.beginTransaction();
		//------------------------
		Serializable save = session.save(user);
		//------------------------
		tx.commit();
		session.close();
		return (int) save;
		
	}
	//根据code查询user
	@Override
	public User getUserByCode(String code) throws Exception {
		/*QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where code =?";
		return queryRunner.query(sql, new BeanHandler<>(User.class),code);*/
		Session session = HibernateUtils.getOpenSession();
		Transaction tx = session.beginTransaction();
		//------------------------
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("code", code));
		User user = (User) criteria.uniqueResult();
		//------------------------
		tx.commit();
		session.close();
		return user;
		
	}
	
	//更新user
	@Override
	public void update(User user) throws Exception {
		QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
		/**
		 * `uid` varchar(32) NOT NULL,  
	 `username` varchar(20) DEFAULT NULL,
	 `password` varchar(20) DEFAULT NULL,
     `name` varchar(20) DEFAULT NULL,
	 `email` varchar(30) DEFAULT NULL,
	 `telephone` varchar(20) DEFAULT NULL,
	 `birthday` date DEFAULT NULL,
	 `sex` varchar(10) DEFAULT NULL,
	 `state` int(11) DEFAULT NULL,  
	 `code` varchar(64) DEFAULT NULL,
		 */
		String sql = "update user set username = ?, password = ?,name = ?,email = ?,telephone = ?,birthday = ?,sex = ?,state = ?, code = ? where uid = ?";
		Object[] params= {user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(), user.getBirthday(),user.getSex(),
				user.getState(),user.getCode(),user.getUid()};
		queryRunner.update(sql,params );
		
	}
	//根据用户名和密码查询用户
	@Override
	public User selectByUserNameAndPwd(String username, String password) throws Exception {
		/*QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
		String sql = "select * from user where username = ? and password = ?";
		return queryRunner.query(sql, new BeanHandler<>(User.class),username,password);*/
		Session session = HibernateUtils.getOpenSession();
		Transaction tx = session.beginTransaction();
		//------------------------
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("username", username));
		criteria.add(Restrictions.eq("password", password));
		User user = (User) criteria.uniqueResult();
		//------------------------
		tx.commit();
		session.close();
		return user;
		
	}

}
