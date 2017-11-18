package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.bean.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.utils.BeanFactory;

import sun.net.www.content.text.plain;

public class CategoryServlet extends BaseServlet {
	public String findAllCategory(HttpServletRequest request,HttpServletResponse response) {
		try {
			// 调用业务
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("CategoryService");
			String json = categoryService.findAllCategory();
			if(json!=null){
				// 分发转向 TODO
	            response.getWriter().println(json);
	            //System.out.println(json);
				
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

   }
	
      
}
