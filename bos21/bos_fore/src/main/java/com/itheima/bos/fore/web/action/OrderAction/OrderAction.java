package com.itheima.bos.fore.web.action.OrderAction;

import com.itheima.bos.domain.take_delivery.Order;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.ws.rs.core.MediaType;

@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class OrderAction extends ActionSupport implements ModelDriven<Order>{
    private Order model=new Order();
    @Override
    public Order getModel() {
        return model;
    }
    @Action(value="orderAction_save",results={@Result(name = "success", location = "/index.html",
            type = "redirect")
            })
    public String save() throws Exception {
        WebClient.create("http://localhost:8080/web/webservice/orderService/save")
                .accept(MediaType.APPLICATION_JSON)// 指定本项目接受的数据格式
                .type(MediaType.APPLICATION_JSON)// 指定本项目传递给对方的数据格式
                .post(model);
        return SUCCESS;
    }
}
