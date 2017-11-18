package com.itheima.bos.web.action.take_delivery;

import com.itheima.bos.domain.take_delivery.WayBill;
import com.itheima.bos.service.take_delivery.WayBillService;
import com.itheima.bos.web.common.BaseAction;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletResponse;
import com.itheima.bos.service.take_delivery.WayBillService;
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class WayBillAction extends BaseAction<WayBill> {
    @Autowired
    private WayBillService wayBillService;

    @Action(value="wayBillAction_save")
    public String save() throws Exception {
        String flag = "1";
        try{
            wayBillService.save(model);
        }catch (Exception e){
            e.printStackTrace();
            flag = "0";
        }
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(flag);

        return NONE;
    }
}
