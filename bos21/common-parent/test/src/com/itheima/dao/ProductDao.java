package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.bean.Product;

public interface ProductDao {
	/**
	 * 查询热门商品
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Product> findHotProductList() throws Exception;

	/**
	 * 查询最新商品
	 * 
	 * @return
	 * @throws Exception
	 */
	List<Product> findPdateProductList() throws Exception;
   /**
    * 点击商品并展示
    * @param pid
    * @return
 * @throws Exception 
    */
	Product showProduct(String pid) throws Exception;
  /**
   * 分页查询： 返回的每个类别商品的总条数
   * @param cid
   * @return
   */
  int findtotalCount(String cid) throws Exception;
/**
 * 分页查询： 返回的是每页显示商品个数的集合
 * @param cid
 * @param a
 * @param b
 * @return
 */
  List<Product> findCurrentPageShowProductCount(String cid, int a, int b) throws Exception;
}
