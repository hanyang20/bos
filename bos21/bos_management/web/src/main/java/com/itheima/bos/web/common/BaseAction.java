package com.itheima.bos.web.common;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import com.itheima.bos.domain.base.Area;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:BaseAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 2, 2017 9:26:18 PM <br/>       
 */
public class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
   protected T model;
   private Class<T> clazz;
   
   protected int page;
   protected int rows;
   public BaseAction(){
       ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
       clazz = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
       try {
           model = clazz.newInstance();
    } catch (Exception e) {
        e.printStackTrace();  
    }
   }
   @Override
   public T getModel() {
       return model;
   }
   public void java2Json(Page page,String[] excludes){
       List<T> list = page.getContent();
       long total = page.getTotalElements();
       Map map=new HashMap<>();
       map.put("rows", list);
       map.put("total", total);
       JsonConfig jsonConfig=new JsonConfig();
       jsonConfig.setExcludes(excludes);
     String json = JSONObject.fromObject(map,jsonConfig).toString();
     ServletActionContext.getResponse().setContentType("applicatopn/json;charset=utf-8");
     try {
        ServletActionContext.getResponse().getWriter().println(json);
    } catch (IOException e) {
        e.printStackTrace();  
        
    }
   }
   public void list2json(List list,String[] excludes){
       JsonConfig jsonConfig=new JsonConfig();
       jsonConfig.setExcludes(excludes);
       // 将数据转化为json字符串
       String json = JSONArray.fromObject(list,jsonConfig).toString();
       // 指定输出内容格式
       ServletActionContext.getResponse().setContentType("application/json;charset=UTF-8");
       // 输出内容
       try {
        ServletActionContext.getResponse().getWriter().write(json);
    } catch (IOException e) {
        e.printStackTrace();  
        
    }
   }
    public void setPage(int page) {
        this.page = page;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
  
