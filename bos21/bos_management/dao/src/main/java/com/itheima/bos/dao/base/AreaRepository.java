package com.itheima.bos.dao.base;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.bos.domain.base.Area;

/**  
 * ClassName:AreaRepository <br/>  
 * Function:  <br/>  
 * Date:     Nov 2, 2017 9:05:45 PM <br/>       
 */
public interface AreaRepository  extends JpaRepository<Area, Long>,JpaSpecificationExecutor<Area> {

//    @Query("from Area a where a.shortcode like ?1 or a.citycode like ?1 or a.province like ?1 or a.city like ?1 or a.district like ?1")
    @Query("from Area a where a.province like ?1 or a.city like ?1 or a.district like ?1 or a.citycode like ?1 or a.shortcode like ?1")
    List<Area> findQ(String q);
    @Query("from Area a where a.province = ?1 and a.city = ?2 and  a.district = ?3")
    Area findByProvinceAndCityAndDistrict(String province, String city,
                                          String district);



}
  
