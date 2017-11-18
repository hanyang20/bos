package com.itheima.service.impl;

import java.util.List;

import com.itheima.bean.PageBean;
import com.itheima.bean.Product;
import com.itheima.constant.Constant;
import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.ProductDaoImpl;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;

public class ProductServiceImpl implements ProductService {
static  ProductDao productDao=(ProductDao) BeanFactory.getBean("ProductDao");
	//热门商品
	@Override
	public List<Product> findHotProductList() throws Exception {
        //ProductDao productDao=(ProductDao) BeanFactory.getBean("ProductDao");
		return  productDao.findHotProductList();
	}
   //最新商品
	@Override
	public List<Product> findPdateProductList() throws Exception {
		//ProductDao productDao =(ProductDao) BeanFactory.getBean("ProductDao");
		return productDao.findPdateProductList();
	}
	//点击商品并展示
	@Override
	public Product showProduct(String pid) throws Exception {
		//ProductDao productDao = (ProductDao) BeanFactory.getBean("ProductDao");
		return productDao.showProduct(pid);
	}
	
	//分页展示分类商品
	@Override
	public PageBean showProductListByCid(String cid, int currentPage) throws Exception {
		PageBean<Product> pageBean=new PageBean<Product>();
		//
		//ProductDao productDao = (ProductDao) BeanFactory.getBean("ProductDao");
		// 显示当前页数private int currentPage;
		pageBean.setCurrentPage(currentPage);
		// 显示当前页数显示的条数private int currentCount;
		pageBean.setCurrentCount(Constant.PRODUCT_PAGE_SIZE);
		// 总条数private int totalCount;,从数据库聚合函数查询
		int totalCount=productDao.findtotalCount(cid);
		pageBean.setTotalCount(totalCount);
		// 总页数private int totalPage;
		int totalPage=0;
		if(totalCount % Constant.PRODUCT_PAGE_SIZE==0){
			totalPage=totalCount/Constant.PRODUCT_PAGE_SIZE;
		}else{
			totalPage=totalCount/Constant.PRODUCT_PAGE_SIZE+1;
		}
		pageBean.setTotalPage(totalPage);
		//每页显示的商品集合private List<T> list;
		int b=Constant.PRODUCT_PAGE_SIZE;
		int a=(currentPage-1)*Constant.PRODUCT_PAGE_SIZE;
		List<Product> list=productDao.findCurrentPageShowProductCount(cid,a,b);
		pageBean.setList(list);
		return pageBean;
	}

}
