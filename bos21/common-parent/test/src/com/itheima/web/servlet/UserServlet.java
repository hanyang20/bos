package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.util.net.AprEndpoint.SendfileData;

import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;

public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
  public String registerUI(HttpServletRequest request,HttpServletResponse response){
	return "/jsp/register.jsp";
	  
  }
  public String loginUI(HttpServletRequest request,HttpServletResponse response){
		return "/jsp/login.jsp";
		  
	  }
  //注册
  public String register(HttpServletRequest request,HttpServletResponse response){
	  try {
			//1.获得请求参数,封装成User对象
			Map<String, String[]> map = request.getParameterMap();
			User user = new User();
			BeanUtils.populate(user, map);
			//1.1 手动封装uid, 状态,激活码
			//user.setUid(UUIDUtils.getId());
			user.setState(Constant.USER_NOT_ACTIVE);
			user.setCode(UUIDUtils.getCode());
			//2.调用业务 (面向接口编程)
			UserService userService = (UserService) BeanFactory.getBean("UserService");
			userService.register(user);
			//3.跳转到信息页面,给用户提示去激活 
			request.setAttribute("msg", "注册成功!赶快去邮箱激活吧~~~");
			return "/jsp/msg.jsp";
		
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "注册失败!");
			return "/jsp/msg.jsp";
		}
	 
  }
  //激活
  public String active(HttpServletRequest request,HttpServletResponse response){
	  User user=null;
	  try {
		String code = request.getParameter("code");
		 //2.调用业务 (面向接口编程)
			UserService userService =  (UserService) BeanFactory.getBean("UserService");
			user = userService.active(code);
			//3.判断user,给用户响应  
			if(user != null){
				request.setAttribute("msg", "激活成功,赶快登录吧~");
				return "/jsp/msg.jsp";
			}else{
				request.setAttribute("msg", "激活失败~");
				return "/jsp/msg.jsp";
			}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		request.setAttribute("msg", "激活失败~");
		return "/jsp/msg.jsp";
	}
	
	  
  }
	 //登录
  public String login(HttpServletRequest request,HttpServletResponse response){
	  User user=null;
	  try {
		String username = request.getParameter("username");
		 String password = request.getParameter("password");
		 
		  UserService userService= (UserService) BeanFactory.getBean("UserService");
		  user = userService.login(username, password);
		if(user!=null){
			//记住用户名，用cookie做一般保存7天
			String checked = request.getParameter("checked");
			
			Cookie cookie=new Cookie("username", username);
			if(checked!=null&&checked.equals("ok")){
				
				cookie.setMaxAge(60*60*24*7);
				cookie.setPath(request.getContextPath());
				//response.addCookie(cookie);
			}else{
				//Cookie cookie=new Cookie("username", username);
				cookie.setMaxAge(0);
				cookie.setPath(request.getContextPath());
				//response.addCookie(cookie);
			}
			response.addCookie(cookie);
			//判断是否激活
			if(user.getState() == Constant.USER_ACTIVED){
				//激活 . 保存登录状态到session, 跳转到网站首页
				request.getSession().setAttribute("user", user);
				response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
				return null;
				
			}else{
				//没有激活
				request.setAttribute("msg", "请先激活....");
				return "/jsp/msg.jsp";
			}

		}else{
			//用户名和密码不匹配
			request.setAttribute("msg", "用户名和密码不匹配...");
			return "/jsp/login.jsp";
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		request.setAttribute("msg", "登录失败...");
		return "/jsp/msg.jsp";
	}
	 
  
  }
  //注销
  public String logout(HttpServletRequest request,HttpServletResponse response){
	  try {
		HttpSession session = request.getSession();
		 session.removeAttribute("user");
		  response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
		  return null;
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	  
	  return null;
  }
}
