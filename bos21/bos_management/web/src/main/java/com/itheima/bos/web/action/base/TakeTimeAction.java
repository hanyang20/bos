package com.itheima.bos.web.action.base;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.TakeTimeService;
import com.itheima.bos.web.common.BaseAction;

/**  
 * ClassName:TakeTimeAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 7:47:01 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class TakeTimeAction extends BaseAction<TakeTime>{
    @Autowired
    private TakeTimeService takeTimeService;
    
    @Action(value="takeTimeAction_findAll")
    public String findAll(){
         List<TakeTime> list=takeTimeService.findAll();
         list2json(list, null);
        return NONE;
    }
}
  
