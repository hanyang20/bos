package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;
import com.itheima.bos.web.common.BaseAction;
import org.apache.shiro.SecurityUtils;
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

import java.io.IOException;
import java.util.List;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class MenuAction extends BaseAction<Menu> {
    @Autowired
    private MenuService menuService;
    // 查询所有的一级菜单项
    @Action(value="menuAction_findAllLevelOne")
    public String findAllLevelOne(){
        List<Menu> list=menuService.findAllLevelOne();
        // 只查询所有的一级菜单,子菜单应该是通过父菜单的childrenMenus属性加载出来
        list2json(list, new String[]{"roles","childrenMenus","parentMenu"});
        return NONE;
    }
    // 分页查询
    @Action("menuAction_pageQuery")
    public String pageQuery() throws IOException {

        // stuts 框架在封装数据的时候,会优先封装给模型对象

        Pageable pageable = new PageRequest(
                Integer.parseInt(model.getPage()) - 1, rows);
        Page<Menu> page = menuService.pageQuery(pageable);

        java2Json(page, new String[] {"roles", "childrenMenus","parentMenu"});

        return NONE;
    }
    @Action(value="menuAction_save",results={@Result(name="success",location="/pages/system/menu.html",type="redirect")})
    public String save() {
        menuService.save(model);
        return SUCCESS;
    }


    // 查询当前用户的菜单项，index.html
    @Action(value="menuAction_findByUser")
    public String findByUser(){
       User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Menu> list=menuService.findMenuListByUser(user);
        // 只查询所有的一级菜单,子菜单应该是通过父菜单的childrenMenus属性加载出来
                                                       //过滤掉多余的children，不然前台显示两份children数据
        list2json(list, new String[]{"roles","childrenMenus","parentMenu","children"});
        return NONE;
    }
}

