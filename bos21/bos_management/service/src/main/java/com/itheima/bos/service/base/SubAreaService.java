package com.itheima.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.bos.domain.base.SubArea;

/**  
 * ClassName:SubareaService <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 4:07:41 PM <br/>       
 */
public interface SubAreaService {

    void save(SubArea model);

    Page<SubArea> pageQuery(Specification<SubArea> specification,
            Pageable pageable);

    List<SubArea> findListNotAssociationFixedArea();

    List<SubArea> findListHasAssociationFixedArea(Long fixedAreaId);

    void assignSubArea2FixedArea(Long fixedAreaId, List<Long> subareaIds);

}
  
