package com.itheima.bos.service.system;

import com.itheima.bos.domain.system.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    //保存用户
    void save(User model, List<Long> roleIds);
     //分页查询
    Page<User> pageQuery(Pageable pageable);
}
