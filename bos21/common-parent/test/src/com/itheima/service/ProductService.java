package com.itheima.service;

import java.util.List;

import com.itheima.bean.PageBean;
import com.itheima.bean.Product;

public interface ProductService {
   /**
    * 热门商品
    * @return
    * @throws Exception
    */
	List<Product> findHotProductList() throws Exception;
   /**
    * 最新商品
    * @return
    * @throws Exception
    */
	List<Product> findPdateProductList() throws Exception;
     /**
      * 点击商品并展示
      * @param pid
      * @return
      */
	Product showProduct(String pid) throws Exception;
	/**
	 * 分页展示分类商品
	 * @param cid
	 * @param currentPage
	 * @return
	 * @throws Exception 
	 */
	PageBean showProductListByCid(String cid, int currentPage) throws Exception;

}
