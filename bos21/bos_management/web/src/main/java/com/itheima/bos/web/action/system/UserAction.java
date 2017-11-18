package com.itheima.bos.web.action.system;

import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;
import com.itheima.bos.web.common.BaseAction;
import com.itheima.crm.domain.Customer;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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

import java.io.IOException;
import java.util.List;

@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
    private String checkCode;

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
     @Autowired
     private UserService userService;
    //登录,使用shiro框架
    @Action(value="userAction_login",results={@Result(name="success",location="/index.html",type="redirect"),
            @Result(name="login",location="/login.html",type="redirect")})
    public String login(){
        String validateCode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
        if(StringUtils.isNotEmpty(validateCode)&&
                StringUtils.isNotEmpty(checkCode)&&
                checkCode.equalsIgnoreCase(validateCode)){
            Subject subject= SecurityUtils.getSubject();
            AuthenticationToken taken=new UsernamePasswordToken(model.getUsername(),model.getPassword());
            try{
                subject.login(taken);
                User user = (User) subject.getPrincipal();
                ServletActionContext.getRequest().getSession().setAttribute("user", user);
                return SUCCESS;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return LOGIN;

    }
    //注销,使用shiro框架
    @Action(value="userAction_logout",results={
            @Result(name="login",location="/login.html",type="redirect")})
    public String logout(){
          Subject subject=SecurityUtils.getSubject();
          subject.logout();
          ServletActionContext.getRequest().getSession().invalidate();
        return LOGIN;

    }

    //保存用户
    private List<Long> roleIds;
    @Action(value="userAction_save",results={@Result(name="success",location="/pages/system/userlist.html",type="redirect")})
    public String save() {
        userService.save(model,roleIds);
        return SUCCESS;
    }
    //分页查询
    @Action(value = "userAction_pageQuery")
    public String pageQuery() throws IOException {
        Pageable pageable=new PageRequest(page-1 ,rows);
        Page<User> page=userService.pageQuery(pageable);
        java2Json(page, new String[]{"roles"});
        return NONE;
    }
    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
