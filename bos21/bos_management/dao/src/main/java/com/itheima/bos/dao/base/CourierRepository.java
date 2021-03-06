package com.itheima.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Courier;

/**  
 * ClassName:CourierRepository <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:57:50 PM <br/>       
 */
public interface CourierRepository extends JpaRepository<Courier, Long>,JpaSpecificationExecutor<Courier> {
   @Modifying
   @Query("update Courier set deltag = '1' where id = ?")
    void deleteBatch(Long parseLong);

}
  
