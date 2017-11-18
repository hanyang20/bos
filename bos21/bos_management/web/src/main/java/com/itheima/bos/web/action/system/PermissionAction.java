package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.service.system.PermissionService;
import com.itheima.bos.web.common.BaseAction;
import org.apache.struts2.convention.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import javax.print.DocFlavor;
import java.io.IOException;
import java.util.List;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class PermissionAction extends BaseAction<Permission> {
     @Autowired
     private PermissionService permissionService;
    // 分页查询
    @Action("permissionAction_findAll")
    public String findAll() throws IOException {
        // 只查询所有的一级菜单,子菜单应该是通过父菜单的childrenMenus属性加载出来
        List<Permission> list = permissionService.findAll();
        list2json(list,new String[] {"roles"});
        return NONE;
    }
    @Action(value = "permissionAction_save",results = {@Result(name = "success",location = "/pages/system/permission.html",type = "redirect")})
    public String save(){
        permissionService.save(model);
        return SUCCESS;
    }
}
