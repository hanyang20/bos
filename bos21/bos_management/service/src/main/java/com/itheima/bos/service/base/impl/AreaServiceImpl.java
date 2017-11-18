package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;

/**  
 * ClassName:AreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 2, 2017 9:04:14 PM <br/>       
 */
@Service
@Transactional
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;
    @Override
    public void importXls(List<Area> list) {
          areaRepository.save(list);
    }
    @Override
    public Page<Area> pageQuery(Pageable pageable) {
          
        return areaRepository.findAll(pageable);
    }

    @Override
    public Page<Area> pageQuery(Specification<Area> specification,
            Pageable pageable) {
          
        return areaRepository.findAll(specification, pageable);
    }
    @Override
    public List<Area> findQ(String q) {
          
        return areaRepository.findQ("%"+q+"%");
    }
    @Override
    public List<Area> findAll() {
          
        return areaRepository.findAll();
    }

}
  
