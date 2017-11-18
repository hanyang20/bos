package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    //普通用户，根据id去查询对应的角色
    @Query("select r from Role r inner join r.users u where u.id = ?")
    List<Role> findRoleListByUserId(Long id);
}
