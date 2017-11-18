package com.itheima.bos.service.base.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.SubAreaRepository;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;

/**  
 * ClassName:SubareaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 4:08:15 PM <br/>       
 */
@Service
@Transactional
public class SubAreaServiceImpl implements SubAreaService {
    @Autowired
    private SubAreaRepository subAreaRepository;
    @Override
    public void save(SubArea model) {
           subAreaRepository.save(model);
    }
    @Override
    public Page<SubArea> pageQuery(Specification<SubArea> specification,
            Pageable pageable) {
          
        return subAreaRepository.findAll(specification, pageable);
    }
    //定区里，没有关联定区区需要的分区数据
    @Override
    public List<SubArea> findListNotAssociationFixedArea() {
          
        return subAreaRepository.findByFixedAreaIdIsnull();
    }
  //定区里，已经关联定区需要的分区数据
    @Override
    public List<SubArea> findListHasAssociationFixedArea(Long fixedAreaId) {
          
        return subAreaRepository.findByFixedAreaId(fixedAreaId);
    }
    //关联分区，
    @Override
    public void assignSubArea2FixedArea(Long fixedAreaId,
            List<Long> subareaIds) {
        //关联分区之前要把，和指定的定区Id关联的分区进行解绑操作
        subAreaRepository.setCFIXEDAREAIDNullByFixedAreaId(fixedAreaId);
       // 再把指定的分区绑定到指定的定区
        if(subareaIds != null && subareaIds.size()>0){
        for (Long id : subareaIds) {
            subAreaRepository.setSubArea2FixedArea(fixedAreaId, id);
        }
       } 
    }

}
  
