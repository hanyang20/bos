package com.itheima.service.impl;

import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.MailUtils;

public class UserServiceImpl implements UserService {
	static UserDao userDao = (UserDao) BeanFactory.getBean("UserDao");
	@Override
	public int register(User user) throws Exception {
		//调用Dao
		//UserDao userDao = (UserDao) BeanFactory.getBean("UserDao");
		int save = userDao.save(user);
		//发送激活邮件  
	//	MailUtils.sendMail(user.getEmail(), "尊敬的"+user.getUsername()+":欢迎注册黑马商城!请点击下面的链接,进行激活.<a href='http://localhost:8080/store_20/userServlet?method=active&code="+user.getCode()+"'>点击激活</a>");
	    MailUtils.sendMail(user.getEmail(), "尊敬的"+user.getUsername()+":欢迎注册黑马商城!请点击下面的链接,进行激活.<a href='http://localhost:8080/user_jihuo?code="+user.getCode()+"'>点击激活</a>");
		return save;
	} 
	/**
	 * 激活
	 */
	@Override
	public User active(String code) throws Exception {
	  //UserDao dao=(UserDao) BeanFactory.getBean("UserDao");
	  User user = userDao.getUserByCode(code);
	  if(user!=null){
		  user.setState(1);
		  user.setCode(null);
		  userDao.update(user);
	  }
		
		return user;
	}
	//登录
	@Override
	public User login(String username, String password) throws Exception {
		//1.调用Dao, 根据用户名好密码查询user, 
		//UserDao userDao =(UserDao) BeanFactory.getBean("UserDao");
		//User user =  userDao.selectByUserNameAndPwd(username,password);
		return  userDao.selectByUserNameAndPwd(username,password);
	}

}
