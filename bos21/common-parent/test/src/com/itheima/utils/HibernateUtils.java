package com.itheima.utils;

import org.eclipse.jdt.internal.compiler.batch.Main;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	private static SessionFactory sessionFactory;
	static{
		Configuration configure = new Configuration().configure();
	  	 sessionFactory = configure.buildSessionFactory();
	}
	//获得session => 获得全新session
public static Session getOpenSession(){
	Session session = sessionFactory.openSession();
  	return session;
}
//获得session => 获得与线程绑定的session
public static Session getOpenCurrentSession(){
	Session session = sessionFactory.getCurrentSession();
	return session;
}
}
