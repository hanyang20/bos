package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;
import com.itheima.bos.web.common.BaseAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
    @Autowired
    private RoleService roleService;
    // 分页查询
    @Action("roleAction_findAll")
    public String findAll() throws IOException {
        // 只查询所有的一级菜单,子菜单应该是通过父菜单的childrenMenus属性加载出来
        List<Role> list = roleService.findAll();
        list2json(list,new String[] {"menus","users","permissions"});
        return NONE;
    }
    //添加角色
    private String menuIds;
    private List<Long> permissionIds;

    @Action(value="roleAction_save",results={@Result(name="success",location="/pages/system/role.html",type="redirect")})
    public String save() {
        roleService.save(model,menuIds,permissionIds);
        return SUCCESS;
    }




    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public void setPermissionIds(List<Long> permissionIds) {
        this.permissionIds = permissionIds;
    }

}
