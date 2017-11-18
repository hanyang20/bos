package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAll();

    void save(Role model, String menuIds, List<Long> permissionIds);
}
