package com.itheima.bos.dao.system;

import com.itheima.bos.domain.system.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu,Long> {
    //查询所有一级菜单
    List<Menu> findByParentMenuIsNull();
    //根据当前用户id查询该用户的菜单项
    @Query("select m from Menu m inner join m.roles r inner join r.users u where u.id = ?")
    List<Menu> findMenuListByUser(Long id);
}
