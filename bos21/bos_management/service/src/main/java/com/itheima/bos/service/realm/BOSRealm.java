package com.itheima.bos.service.realm;

import com.itheima.bos.dao.base.UserRepository;
import com.itheima.bos.dao.system.MenuRepository;
import com.itheima.bos.dao.system.PermissionRepository;
import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BOSRealm extends AuthorizingRealm {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 为用户授权,只需把用户需要的权限添加到info中就可以了
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        // 为用户添加area权限
//        info.addStringPermission("area");
//        info.addStringPermission("courier:delete");
//        // 为用户添加角色
//        info.addRole("zs");
        //获取当前用户
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        //admin为系统默认帐户，有最高权限
        List<Role> role_list=null;
        List<Permission> permission_list=null;
        if(user.getUsername().equals("root")){
            //如果是admin的话，菜单和权限都查询全部
            role_list = roleRepository.findAll();
            for(Role role:role_list){
                info.addStringPermission(role.getKeyword());
            }
            permission_list=permissionRepository.findAll();
            for(Permission permission:permission_list){
                 info.addStringPermission(permission.getKeyword());
            }
        }else{
             //如果是普通用户，就根据当前用户id去查询对应的角色和权限集合
            role_list=roleRepository.findRoleListByUserId(user.getId());
            for(Role role:role_list){
                info.addStringPermission(role.getKeyword());
            }
            permission_list=permissionRepository.findPermissionListByUserId(user.getId());
            for(Permission permission:permission_list){
                info.addStringPermission(permission.getKeyword());
            }
        }
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken taken) throws AuthenticationException {
        System.out.println("shiro框架的认证方法调用了。。。");
        UsernamePasswordToken token= (UsernamePasswordToken) taken;
        String username = ((UsernamePasswordToken) taken).getUsername();
        User user = userRepository.findByUsername(username);
        if(user==null){
            return null;
        }
        AuthenticationInfo info= new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());

        return info;
    }
}
