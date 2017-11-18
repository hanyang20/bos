package com.itheima.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 7:59:47 PM <br/>       
 */
public interface FixedAreaService {

    void save(FixedArea model);

    Page<FixedArea> pageQuery(Pageable pageable);
    //定区，快递员，快递员上班时间的id
    void associationCourierToFixedAreaAndTakeTimeToCourier(Long id,
            Long courierId, Long takeTimeId);

    Page<FixedArea> pageQuery(Specification<FixedArea> specification,
            Pageable pageable);

}
  
