package com.itheima.dao;


import com.itheima.bean.User;

public interface UserDao {
	/**
	 * 保存用户(注册)
	 * @param user
	 * @return 
	 */
	int save(User user)  throws Exception;

	/**
	 * 根据code查询user
	 * @param code
	 * @return
	 * @throws Exception
	 */
	User getUserByCode(String code)throws Exception;

	/**
	 * 更新user
	 * @param user
	 * @throws Exception
	 */
	void update(User user)throws Exception;
	/**
	 * 根据用户名和密码查询用户(登录操作)
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	User selectByUserNameAndPwd(String username, String password)throws Exception;

}
