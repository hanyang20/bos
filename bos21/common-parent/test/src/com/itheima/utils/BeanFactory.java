package com.itheima.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class BeanFactory {
   public static Object getBean(String id){
	   try {
		   //1.通过类加载器获取流，读进来
		   Document doc = new SAXReader().read(BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml"));
			//2.调用api selectSingleNode(表达式),
		   Element ele = (Element) doc.selectSingleNode("//bean[@id='"+id+"']");
			//3.ele是class父标签 获取元素的class属性
		   String path = ele.attributeValue("class");
			//4.通过反射返回实现类的对象
		   Object object = Class.forName(path).newInstance();
		   return object;
	} catch (Exception e) {
		e.printStackTrace();
		return "返回bean失败";
	} 
   }
  
}
