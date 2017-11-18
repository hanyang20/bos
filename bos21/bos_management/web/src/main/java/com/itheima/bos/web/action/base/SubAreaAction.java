package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.SubAreaService;
import com.itheima.bos.web.common.BaseAction;

/**  
 * ClassName:SubareaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 4, 2017 4:03:06 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea>{
    @Autowired
    private SubAreaService subareaService;
    @Action(value="subareaAction_save",results={@Result(name="success",location="/pages/base/sub_area.html",type="redirect")})
    public String save() {
        subareaService.save(model);
        return SUCCESS;
    }
    
    @Action(value = "subareaAction_pageQuery")
    public String pageQuery() throws IOException {
        Specification<SubArea> specification=new Specification<SubArea>() {

            @Override
            public Predicate toPredicate(Root<SubArea> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                Area area = model.getArea();
                String keyWords = model.getKeyWords();
                FixedArea fixedArea = model.getFixedArea();
                List<Predicate> list=new ArrayList<>();
                //公司编号
                if(StringUtils.isNotBlank(keyWords)){
                    Predicate p1 = cb.equal(root.get("keyWords").as(String.class), keyWords);
                    list.add(p1);
                }
                if(area!=null){
                    if(StringUtils.isNotBlank(area.getProvince())){
                        // 因为Standard是对象,也就是SubArea的关联对象,所以需要先获取关联对象,再去执行查询
                        Join<Object, Object> join = root.join("area");
                        Predicate p2 = cb.like(join.get("province").as(String.class), "%"+area.getProvince()+"%");
                        list.add(p2);
                    }
                    if(StringUtils.isNotBlank(area.getCity())){
                        // 因为Standard是对象,也就是SubArea的关联对象,所以需要先获取关联对象,再去执行查询
                        Join<Object, Object> join = root.join("area");
                        Predicate p3 = cb.like(join.get("city").as(String.class), "%"+area.getCity()+"%");
                        list.add(p3);
                    }
                    if(StringUtils.isNotBlank(area.getDistrict())){
                        // 因为Standard是对象,也就是SubArea的关联对象,所以需要先获取关联对象,再去执行查询
                        Join<Object, Object> join = root.join("area");
                        Predicate p4 = cb.like(join.get("district").as(String.class), "%"+area.getDistrict()+"%");
                        list.add(p4);
                    }
                }
                      //通过定区id查询关联的分区，定区里的查询
                     if(fixedArea!=null){
                         if(fixedArea.getId()>0){
                         // 因为Standard是对象,也就是SubArea的关联对象,所以需要先获取关联对象,再去执行查询
                         Join<Object, Object> join = root.join("fixedArea");
                         Predicate p5 = cb.equal(join.get("id").as(Long.class), fixedArea.getId());
                         list.add(p5);
                         }
                     }
                // 如果查询条件为空,返回空
                if(list.size()==0){
                    return null;
                }
                Predicate[] arr=new Predicate[list.size()];
                list.toArray(arr);
                return cb.and(arr);
            }};
        Pageable pageable=new PageRequest(page-1 ,rows);
        Page<SubArea> page=subareaService.pageQuery(specification,pageable);
       java2Json(page, new String[]{"fixedArea","subareas"});
        return NONE;
    }
   //查询未关联定区的分区
  @Action("subareaAction_findListNotAssociationFixedArea")
  public String findListNotAssociationFixedArea(){
      List<SubArea> list=subareaService.findListNotAssociationFixedArea();
      list2json((List) list, new String[]{"fixedArea","subareas"});
    return NONE;
      
  }
  //查询已关联定区的分区
  private Long fixedAreaId;
  @Action("subareaAction_findListHasAssociationFixedArea")
  public String findListHasAssociationFixedArea(){
      List<SubArea> list=subareaService.findListHasAssociationFixedArea(fixedAreaId);
      list2json(list, new String[]{"fixedArea","subareas"});
    return NONE;
      
  }
  //关联定区，获得了定区的fixedAreaId和各个分区的subareaIds并封装到集合里面
  private List<Long> subareaIds;
  @Action(value="subareaAction_assignSubArea2FixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
  public String assignSubArea2FixedArea(){
      subareaService.assignSubArea2FixedArea(fixedAreaId,subareaIds);
    return SUCCESS;
  }

  
  
  
  
  
  public void setFixedAreaId(Long fixedAreaId) {
    this.fixedAreaId = fixedAreaId;
}
  public void setSubareaIds(List<Long> subareaIds) {
      this.subareaIds = subareaIds;
  }
  
  
}
  
