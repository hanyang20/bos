package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long> {
    //普通用户，根据用户id查询对应的权限
    @Query("select p from Permission p inner join p.roles r inner join r.users u where u.id = ?")
    List<Permission> findPermissionListByUserId(Long id);
}
