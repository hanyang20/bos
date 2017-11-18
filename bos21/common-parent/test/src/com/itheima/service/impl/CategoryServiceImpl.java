package com.itheima.service.impl;

import java.util.List;

import com.google.gson.Gson;
import com.itheima.bean.Category;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {
	static CategoryDao categoryDao=(CategoryDao) BeanFactory.getBean("CategoryDao");

	@Override
	public String findAllCategory() throws Exception {
		//调用dao层
		//CategoryDao categoryDao=new CategoryDaoImpl();
		
		//创建redis对象
		Jedis jedis = JedisUtils.getJedis();
		String json = jedis.get("json");
		
		if(json==null){
			System.out.println("redis数据库没有，去mysql查询");
			List<Category> categoryList=categoryDao.findAllCategory();
			Gson gson=new Gson();
			 json = gson.toJson(categoryList);
			jedis.set("json", json);
		}
		JedisUtils.closeJedis(jedis);
		return json;
	}

}
