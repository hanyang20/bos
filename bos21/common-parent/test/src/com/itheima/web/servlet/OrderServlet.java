package com.itheima.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.bean.Cart;
import com.itheima.bean.CartItem;
import com.itheima.bean.OrderItem;
import com.itheima.bean.Orders;
import com.itheima.bean.PageBean;
import com.itheima.bean.Product;
import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.service.OrderService;
import com.itheima.service.impl.OrderServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;

/**
 * 
 */
public class OrderServlet extends BaseServlet{
	//提交订单
	public String submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		//提交订单之前要判断user是否为空
		if(user==null){
			//重定向到登陆页面
			response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		}
		//关键就是封装orders对象
		Orders orders=new Orders();
		// private String oid;
		//orders.setOid(UUIDUtils.getId());
		// private Date ordertime;
		orders.setOrdertime(new Date());
		// private double total;
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart!=null){
		orders.setTotal(cart.getTotal());
		}
		// private Integer state;//订单状态 0:未付款 1:已付款 2:已发货 3.已完成
		orders.setState(Constant.WEI_FU_KUAN);
		// private String address;
		// private String name;
		// private String telephone;
		// //表示当前订单属于那个用户
		// private User user;
		orders.setUser(user);
		// //表示当前订单包含的订单项
		// private List<OrderItem> orderItems = new ArrayList<>();
		Set<OrderItem> orderItems = new HashSet<>();
		if(cart!=null){
			Collection<CartItem> cartItems = cart.getValues();
			for (CartItem cartItem : cartItems) {
				OrderItem orderItem=new OrderItem();
				// private String itemid;
             // orderItem.setItemid(UUIDUtils.getId());
				// private int count;
                int count = cartItem.getCount();
               orderItem.setCount(count);
				// private double subtotal;
               double subTotal = cartItem.getSubTotal();
               orderItem.setSubtotal(subTotal);
				// 表示包含那个商品private Product product;
                 Product product = cartItem.getProduct();
                 orderItem.setProduct(product);
				// 表示属于那个订单private Orders orders;
                 orderItem.setOrders(orders);
                 //把每个订单项添加到订单项集合中
                 orderItems.add(orderItem);
			}
		   
		}
			orders.setOrderItems(orderItems);
		
			//调用业务
			OrderService service=(OrderService) BeanFactory.getBean("OrderService");
			service.submitOrder(orders);
			session.setAttribute("orders", orders);
			response.sendRedirect(request.getContextPath()+"/jsp/order_info.jsp");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//我的订单
	public String myOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User) request.getSession().getAttribute("user");
			if(user==null){
				response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
			}
			//获取当前页
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			//int currentPage=1;
			int currentCount=3;
			OrderService service=(OrderService) BeanFactory.getBean("OrderService");
			////表示当前订单包含的订单项
			PageBean<Orders> pageBean=service.fidByUid(user,currentPage,currentCount);
			//显示数据
			request.setAttribute("pageBean", pageBean);
			return "/jsp/order_list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	//点击付款，跳到付款页面
	public String findOrdersByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Orders orders=null;
		try {
			String oid = request.getParameter("oid");
			System.out.println(oid);
			OrderService service=(OrderService) BeanFactory.getBean("OrderService");
			orders = service.findOrdersByOid(oid);
			//System.out.println(orders);
			request.getSession().setAttribute("orders", orders);
			response.sendRedirect(request.getContextPath()+"/jsp/order_info.jsp");
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}