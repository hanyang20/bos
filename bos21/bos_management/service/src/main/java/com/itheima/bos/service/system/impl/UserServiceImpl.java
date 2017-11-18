package com.itheima.bos.service.system.impl;

import com.itheima.bos.dao.base.UserRepository;
import com.itheima.bos.domain.system.Role;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user, List<Long> roleIds) {
        userRepository.save(user);
        if(roleIds!=null&&roleIds.size()>0){
            for(Long roleId:roleIds){
                Role role=new Role();
                role.setId(roleId);
                user.getRoles().add(role);
            }
        }
    }

    @Override
    public Page<User> pageQuery(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
