package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MenuService {
    List<Menu> findAllLevelOne();

    Page<Menu> pageQuery(Pageable pageable);

    void save(Menu model);

    List<Menu> findMenuListByUser(User user);
}
