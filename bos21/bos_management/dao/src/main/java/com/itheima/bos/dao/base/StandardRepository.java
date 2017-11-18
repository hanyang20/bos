package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.Standard;
import java.lang.Long;

/**  
 * ClassName:StandardRepository <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 8:56:46 AM <br/>       
 */
public interface StandardRepository extends JpaRepository<Standard, Long> {
    @Override
    List<Standard> findAll();
    
    Standard findById(Long id);
}
  
