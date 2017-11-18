package com.itheima.bos.dao.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.domain.base.TakeTime;

/**  
 * ClassName:TakeTimeRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 7:50:49 PM <br/>       
 */
public interface TakeTimeRepository extends JpaRepository<TakeTime, Long> {
}
  
