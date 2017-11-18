package com.itheima.bos.service.system.impl;

import com.itheima.bos.dao.system.RoleRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.Permission;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.service.system.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void save(Role role, String menuIds, List<Long> permissionIds) {
        roleRepository.save(role);
        //菜单操作
        if(StringUtils.isNoneEmpty(menuIds)){
            String[] split = menuIds.split(",");
            for(String s : split){
                long menuId = Long.parseLong(s);
                Menu menu=new Menu();
                menu.setId(menuId);
                //菜单已经放弃了外键维护，所以要用角色关联菜单
                role.getMenus().add(menu);
            }
        }
        //权限操作
        if(permissionIds!=null&&permissionIds.size()>0){
            for(Long permissionId:permissionIds){
                Permission permission=new Permission();
                permission.setId(permissionId);
                //权限已经放弃了外键维护，所以要用角色关联权限
                role.getPermissions().add(permission);
            }
        }
    }
}
