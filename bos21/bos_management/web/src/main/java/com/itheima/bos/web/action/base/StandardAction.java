package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.standard.PageRanges;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.StandardService;
import com.itheima.bos.web.common.BaseAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**  
 * ClassName:StandardAction <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 8:46:57 PM <br/>       
 */
@Namespace("/") // 等价于以前的struts.xml中<package>节点中的namespace属性
@ParentPackage("struts-default") // 等价于以前的struts.xml中<package>节点中的extends属性
@Controller // 等于在applicationContext.xml中注册了这个bean,代表本类是一个web层的代码
@Scope("prototype") // // 等于在applicationContext.xml中注册本对象为多例
public class StandardAction extends BaseAction<Standard> {


    @Autowired
    private StandardService service;

    // value = <action>节点中的name属性
    // name = <result>节点的name属性
    // location = <result>节点中间的路径
    // type = 指定使用转发还是重定向
    @Action(value = "standardAction_save", results = {@Result(name = "success",
            location = "/pages/base/standard.html", type = "redirect")})
    public String save() {
        service.save(model);

        return SUCCESS;
    }
    @Action(value = "standardAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable=new PageRequest(page-1 ,rows);
        Page<Standard> page=service.pageQuery(pageable);
        java2Json(page, null);
        return NONE;
    }
    @Action(value = "standardAction_findAll")
    public String findAll() throws IOException {
          List<Standard> list=service.findAll();
      String json = JSONArray.fromObject(list).toString();
      ServletActionContext.getResponse().setContentType("applicatopn/json;charset=utf-8");
      ServletActionContext.getResponse().getWriter().println(json);
        return NONE;
    }
    
}

  
