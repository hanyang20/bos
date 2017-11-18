package com.itheima.service;

import com.itheima.bean.User;


public interface UserService {
	
	/**
	 * 注册
	 * @param user
	 */
	int register(User user) throws Exception ;
	
	/**
	 * 激活
	 * @param code 激活码
	 * @return User: 根据激活码获得user对象
	 * @throws Exception
	 */
	User active(String code)throws Exception ;

	/**
	 * 登录
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	User login(String username, String password)throws Exception ;

	

	

}
