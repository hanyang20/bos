package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                   response.setContentType("text/html;charset=utf-8");
		           String methodStr = request.getParameter("method");
                   Class clazz=this.getClass();
                   try {
					Method method=clazz.getMethod(methodStr, HttpServletRequest.class,HttpServletResponse.class);
				String path=(String) method.invoke(this, request,response);
				if(path!=null){
					request.getRequestDispatcher(path).forward(request, response);
				}
                   } catch (Exception e) {
				
					e.printStackTrace();
				}
	}

}
