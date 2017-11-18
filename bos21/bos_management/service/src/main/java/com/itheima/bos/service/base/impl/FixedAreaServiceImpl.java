package com.itheima.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.base.CourierRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.base.TakeTimeRepository;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.TakeTime;
import com.itheima.bos.service.base.FixedAreaService;

/**  
 * ClassName:FixedAreaServiceImpl <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 8:00:10 PM <br/>       
 */
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private TakeTimeRepository takeTimeRepository;
    @Autowired
    private CourierRepository courierRepository;
    @Override
    public void save(FixedArea model) {
          fixedAreaRepository.save(model);
        
    }
    @Override
    public Page<FixedArea> pageQuery(Pageable pageable) {
          
        return fixedAreaRepository.findAll(pageable);
    }
 // 完成快递员和定区的关联，还有快递员和上班时间的关联
    @Override
    public void associationCourierToFixedAreaAndTakeTimeToCourier(Long id,
            Long courierId, Long takeTimeId) {
           //通过id获取定区
          FixedArea fixedArea = fixedAreaRepository.getOne(id);
          //通过courierId获取快递员
          Courier courier = courierRepository.getOne(courierId);
          //通过takeTimeId获取快递员的上班时间
          TakeTime takeTime = takeTimeRepository.getOne(takeTimeId);
        
          //定区关联快递员
          fixedArea.getCouriers().add(courier);
//          courier.getFixedAreas().add(fixedArea);
          //快递员关联上班时间
          courier.setTakeTime(takeTime);
    }
    @Override
    public Page<FixedArea> pageQuery(Specification<FixedArea> specification,
            Pageable pageable) {
          
        return fixedAreaRepository.findAll(specification, pageable);
    }

}
  
