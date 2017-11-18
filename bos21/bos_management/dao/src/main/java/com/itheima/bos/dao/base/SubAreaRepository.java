package com.itheima.bos.dao.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 4:09:35 PM <br/>       
 */
public interface SubAreaRepository extends JpaRepository<SubArea, Long>,JpaSpecificationExecutor<SubArea>{
     //查找为关联定区的分区
    @Query(value="select * from T_SUB_AREA where C_FIXEDAREA_ID is null",nativeQuery = true)
    List<SubArea> findByFixedAreaIdIsnull();
   //查找已关联定区的分区
    @Query(value="select * from T_SUB_AREA where C_FIXEDAREA_ID = ?",nativeQuery = true)
    List<SubArea> findByFixedAreaId(Long fixedAreaId);
    
    //关联分区之前要把，和指定的定区Id关联的分区进行解绑操作
    @Modifying
    @Query(value="update t_sub_area set c_fixedarea_id = null where c_fixedarea_id = ?",nativeQuery = true)
    void setCFIXEDAREAIDNullByFixedAreaId(Long fixedAreaId);
 
    
    // 指定的分区绑定到指定的定区
    @Modifying
    @Query(value="update t_sub_area set c_fixedarea_id = ? where c_id = ?",nativeQuery=true)
    void setSubArea2FixedArea(Long fixedAreaId,Long id);
}
  
