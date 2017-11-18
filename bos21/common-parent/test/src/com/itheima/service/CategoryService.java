package com.itheima.service;

import java.util.List;

import com.itheima.bean.Category;

public interface CategoryService {
   //查询所有分类商品,返回json数组
	
	String findAllCategory() throws Exception;

	
}
