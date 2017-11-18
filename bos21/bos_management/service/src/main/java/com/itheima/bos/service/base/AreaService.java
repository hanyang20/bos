package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.itheima.bos.domain.base.Area;

/**  
 * ClassName:AreaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 2, 2017 9:04:01 PM <br/>       
 */
public interface AreaService{

    void importXls(List<Area> list);

    Page<Area> pageQuery(Pageable pageable);

    Page<Area> pageQuery(Specification<Area> specification, Pageable pageable);

    List<Area> findQ(String q);

    List<Area> findAll();

}
  
