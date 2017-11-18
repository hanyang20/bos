package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.omg.CORBA.LongHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.service.base.CourierService;

/**  
 * ClassName:CourierServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:55:59 PM <br/>       
 */
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
    @Autowired
    private CourierRepository courierRepository;
    @Override
    public void save(Courier model) {
          courierRepository.save(model);
        
    }
    @Override
    public Page<Courier> pageQuery(Pageable pageable) {
        return courierRepository.findAll(pageable);
    }
    @RequiresPermissions("courier:delete")
    @Override
    public void deleteBatch(String ids) {
        //逻辑删除，不是物理删除
            if(StringUtils.isNotBlank(ids)){
                String[] split = ids.split(",");
                for (String id : split) {
                    courierRepository.deleteBatch(Long.parseLong(id));
                }
            }
            
        }
    @Override
    public Page<Courier> pageQuery(Specification<Courier> specification,
            Pageable pageable) {
        return courierRepository.findAll(specification, pageable);
    }
    @Override
    public List<Courier> findAll() {
          
        return courierRepository.findAll();
    }        
    }


  
