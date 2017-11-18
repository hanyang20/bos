package com.itheima.action;

import org.apache.struts2.ServletActionContext;

import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sun.org.apache.bcel.internal.classfile.Code;

public class UserAction extends ActionSupport implements ModelDriven<User>{
   private User user=new User();
   private String code;
  
	public void setCode(String code) {
	this.code = code;
}
	public String getCode() {
		return code;
	}
	public String register() throws Exception {
		user.setState(Constant.USER_NOT_ACTIVE);
		user.setCode(UUIDUtils.getCode());
		//2.调用业务 (面向接口编程)
		UserService userService = (UserService) BeanFactory.getBean("UserService");
		int save = userService.register(user);
		//3.跳转到信息页面,给用户提示去激活 
		if(save == 1){
			ServletActionContext.getRequest().setAttribute("msg", "注册成功!赶快去邮箱激活吧~~~");
			return "success";
		}else{
			ServletActionContext.getRequest().setAttribute("msg", "注册失败!");
			return "failed";
		}
    	
    }
	
	public String jihuo() throws Exception {
		 //2.调用业务 (面向接口编程)
		UserService userService =  (UserService) BeanFactory.getBean("UserService");
		User user = userService.active(code);
		//3.判断user,给用户响应  
		if(user != null){
			//request.setAttribute("msg", "激活成功,赶快登录吧~");
			ServletActionContext.getRequest().setAttribute("msg", "激活成功,赶快登录吧~");
			return "success";
		}else{
			//request.setAttribute("msg", "激活失败~");
			ServletActionContext.getRequest().setAttribute("msg", "激活失败~");
			return "failed";
		}
	}
	

	@Override
	public User getModel() {
		return user;
	}
}
