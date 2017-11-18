package com.itheima.dao;

import java.util.List;

import com.itheima.bean.Category;

public interface CategoryDao {
   //查询所有分类商品
	List<Category> findAllCategory() throws Exception;

	
}
