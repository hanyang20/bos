package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itheima.bean.Cart;
import com.itheima.bean.CartItem;
import com.itheima.bean.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.BeanFactory;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet{
	//添加商品到购物车
	public String addProductToCart(HttpServletRequest request, HttpServletResponse response){
	    try {
			//接受参数
			String pid = request.getParameter("pid");
			int count = Integer.parseInt(request.getParameter("count"));
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			if(cart==null){
			   cart=new Cart();
			}
			ProductService service=(ProductService) BeanFactory.getBean("ProductService");
			Product product = service.showProduct(pid);
			CartItem cartItem=new CartItem();
			cartItem.setProduct(product);
			cartItem.setCount(count);
			cart.addProductToCart(cartItem);
			
			//分发转向
			request.getSession().setAttribute("cart", cart);
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			return null;
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	//删除商品
	public String deleteProductToCart(HttpServletRequest request, HttpServletResponse response){
		
		try {
			String pid = request.getParameter("pid");
			Cart cart = (Cart) request.getSession().getAttribute("cart");
			cart.deleteProduct(pid);
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	//清空购物车
	public String clearCart(HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			session.removeAttribute("cart");
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}