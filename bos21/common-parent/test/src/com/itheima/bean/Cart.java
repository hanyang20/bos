package com.itheima.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
     //总金额
	private double total;
	//一个购物项map集合
	private Map<String, CartItem> cartItems=new HashMap<String, CartItem>();
	//获得cartItems中的每一个CartItem
	public Collection<CartItem> getValues(){
		Collection<CartItem> values = cartItems.values();
		return values;
	}
	   //添加商品
		public void addProductToCart(CartItem cartItem){
			//得到添加商品的pid
			String pid = cartItem.getProduct().getPid()+"";
			int count = 0;
			if(cartItems.containsKey(pid)){
				 //如果包含该pid就把购买数量拿出来，相加
				CartItem oldItem = cartItems.get(pid);
				count = cartItem.getCount()+oldItem.getCount();
				//总金额增加
				total+=cartItem.getSubTotal();
				oldItem.setCount(count);
				
			}else{
				//如果不包含该pid，直接设置进去
				cartItems.put(pid, cartItem);
				//总金额增加
				total+=cartItem.getSubTotal();
			}
			
		}
		//删除商品，就是删除购物项，通过pid删除
		public void deleteProduct(String pid){
			//删除商品，就是删除购物项
			CartItem cartItem = cartItems.remove(pid);
			//总金额要减少
			total-=cartItem.getSubTotal();
			
		}
		
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}
}
