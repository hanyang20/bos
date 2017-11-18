package com.itheima.bos.service.system.impl;

import com.itheima.bos.dao.system.MenuRepository;
import com.itheima.bos.domain.system.Menu;
import com.itheima.bos.domain.system.User;
import com.itheima.bos.service.system.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuRepository menuRepository;
    @Override
    public List<Menu> findAllLevelOne() {
        return menuRepository.findByParentMenuIsNull();
    }


    @Override
    public Page<Menu> pageQuery(Pageable pageable) {
        return menuRepository.findAll(pageable);
    }

    @Override
    public void save(Menu model) {
        Menu parentMenu = model.getParentMenu();
        // 如果父菜单项的Id不存在,或者id为0,说明现在添加的菜单是一级菜单,设置它的父菜单项为null
        if(parentMenu!=null&&parentMenu.getId()==0){
            model.setParentMenu(null);
        }
        menuRepository.save(model);
    }

    @Override
    public List<Menu> findMenuListByUser(User user) {
        List<Menu> list=null;
        //如果当前用户是root，就获取所有的菜单项
        if(user.getUsername().equals("root")){
            list=menuRepository.findAll();
        }else{
            //普通用户所显示的菜单项
             list = menuRepository.findMenuListByUser(user.getId());
        }

        return list;
    }
}
