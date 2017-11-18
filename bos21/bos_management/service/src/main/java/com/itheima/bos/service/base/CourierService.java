package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierService <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:55:39 PM <br/>       
 */
public interface CourierService {

    void save(Courier model);

    Page<Courier> pageQuery(Pageable pageable);

    void deleteBatch(String ids);

    Page<Courier> pageQuery(Specification<Courier> specification,
            Pageable pageable);

    List<Courier> findAll();

}
  
