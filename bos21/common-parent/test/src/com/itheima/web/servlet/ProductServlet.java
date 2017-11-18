package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.bean.PageBean;
import com.itheima.bean.Product;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.BeanFactory;

/**
 *
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
   //首页
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//调用业务
			ProductService productService=(ProductService) BeanFactory.getBean("ProductService");
			List<Product> hot_list=productService.findHotProductList();
			List<Product> pdate_list=productService.findPdateProductList();
			request.setAttribute("hot_list", hot_list);
			request.setAttribute("pdate_list", pdate_list);
			//System.out.println(hot_list.toString());
			//System.out.println(pdate_list.toString());
			//分发转向
			return "/jsp/index.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询商品失败。。。");
			return "/jsp/msg.jsp";
		}
	
	}
	//点击商品，展示商品信息
	public String showProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//接受请求参数
			String pid = request.getParameter("pid");
			//调用业务
			ProductService productService=(ProductService) BeanFactory.getBean("ProductService");
			Product product=productService.showProduct(pid);
			request.setAttribute("product", product);
			//System.out.println(product.toString());
			return "/jsp/product_info.jsp";
		} catch (Exception e) {
			
			e.printStackTrace();
			request.setAttribute("msg", "展示商品失败。。。");
			return "/jsp/msg.jsp";
		}
		
	}
	//分页展示分类商品，通过cid
	public String showProductListByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cid = request.getParameter("cid");
		
		String c = request.getParameter("currentPage");
		int currentPage=Integer.parseInt(c);
		ProductService productService=(ProductService) BeanFactory.getBean("ProductService");
		PageBean pageBean=null;
		try {
			 pageBean=productService.showProductListByCid(cid,currentPage);
			request.setAttribute("pageBean", pageBean);
			//System.out.println(pageBean.toString());
			return "/jsp/product_list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询商品失败");
			return "/jsp/msg.jsp";
		}
		
	}
}
