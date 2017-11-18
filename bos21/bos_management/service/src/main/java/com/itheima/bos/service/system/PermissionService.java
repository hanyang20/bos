package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.Permission;

import java.util.List;

public interface PermissionService {
    List<Permission> findAll();

    void save(Permission model);
}
