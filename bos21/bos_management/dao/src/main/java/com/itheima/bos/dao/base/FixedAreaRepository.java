package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.itheima.bos.domain.base.FixedArea;

/**  
 * ClassName:FixedAreaRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 8:01:30 PM <br/>       
 */
public interface FixedAreaRepository extends JpaRepository<FixedArea, Long>,JpaSpecificationExecutor<FixedArea>{

}
  
