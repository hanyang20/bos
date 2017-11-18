package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.print.attribute.standard.PageRanges;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
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

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.service.base.FixedAreaService;
import com.itheima.bos.web.common.BaseAction;
import com.itheima.crm.domain.Customer;

/**  
 * ClassName:FixedAreaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 5, 2017 7:57:33 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class FixedAreaAction extends BaseAction<FixedArea>{
   @Autowired
   private FixedAreaService fixedAreaService;
    
    @Action(value="fixedAreaAction_save",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String save() {
        fixedAreaService.save(model);
        return SUCCESS;
    }
    @Action("fixedAreaAction_pageQuery")
    public String pageQuery() throws IOException {
        Specification<FixedArea> specification=new Specification<FixedArea>() {
            @Override
            public Predicate toPredicate(Root<FixedArea> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                  Long id = model.getId();
                  Set<SubArea> subareas = model.getSubareas();
                  Set<Courier> couriers = model.getCouriers();
                  List<Predicate> list=new ArrayList<>();
                  if(id != null){
                      Predicate p1 = cb.equal(root.get("id").as(Long.class), id);
                      list.add(p1);
                  }
                  if(subareas!=null && subareas.size()>0){
                      for (SubArea subarea : subareas) {
                        if(StringUtils.isNotBlank(subarea.getKeyWords())){
                            Join<Object, Object> join = root.join("subarea");
                            Predicate p2 = cb.equal(join.get("keyWords").as(String.class), subarea.getKeyWords());
                            list.add(p2);
                        }
                    }
                  }
                  if(couriers!=null && couriers.size()>0){
                      for (Courier courier : couriers) {
                        if(StringUtils.isNotBlank(courier.getCompany())){
                            Join<Object, Object> join = root.join("courier");
                            Predicate p3 = cb.equal(join.get("company").as(String.class), courier.getCompany());
                            list.add(p3);
                        }
                    }
                  }
                  if(list.size()==0){
                      return null;
                  }
                  Predicate[] arr=new Predicate[list.size()];
                  list.toArray(arr);
                return cb.and(arr);
            }};
        Pageable pageable=new PageRequest(page-1,rows);
        Page<FixedArea> page=fixedAreaService.pageQuery(specification,pageable);
        java2Json(page,new String[] {"subareas", "couriers"});
        return NONE;
    }
    //查找未关联到定区的客户
    @Action(value = "fixedAreaAction_findListNotAssociation")
    public String findListNotAssociation(){
        List<Customer> list= (List<Customer>) WebClient
                .create("http://localhost:8180/crm20/webservice/customerService/findUnAssociatedCustomers")
                .getCollection(Customer.class);
        list2json(list, null);
        return NONE;
    }
  //查找已关联到定区的客户
    @Action("fixedAreaAction_findListHasAssociation")
    public String findListHasAssociation(){
        List<Customer> list= (List<Customer>) WebClient
                .create("http://localhost:8180/crm20/webservice/customerService/findCustomersAssociated2FixedArea")
                .query("fixedAreaId", model.getId())
                .getCollection(Customer.class);
                list2json(list, null);
                return NONE;
    }
    @Action("fixedAreaAction_findListHasAssociation02")
    public String findListHasAssociation02(){
        Specification<Customer> specification=new Specification<Customer>() {
            @Override
            public Predicate toPredicate(Root<Customer> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                  Long id = model.getId();
                  List<Predicate> list=new ArrayList<>();
                  if(id != null){
                      Predicate p1 = cb.equal(root.get("id").as(Long.class), id);
                      list.add(p1);
                  }
                  if(list.size()==0){
                      return null;
                  }
                  Predicate[] arr=new Predicate[list.size()];
                  list.toArray(arr);
                return cb.and(arr);
            }};
        Pageable pageable=new PageRequest(page-1,rows);
        Page<Customer> page=  (Page<Customer>) WebClient
                .create("http://localhost:8180/crm20/webservice/customerService/findCustomersAssociated2FixedArea02")
                .query("specification", specification)
                .query("pageable", pageable)
                .getCollection(Customer.class);
           java2Json(page, null);
        return NONE;
    }
    
  // 完成客户数据和定区的关联
    private List<Long> customerIds;
    // 完成客户数据和定区的关联
    @Action(value = "fixedAreaAction_assignCustomers2FixedArea",
            results = {@Result(name = "success",
                    location = "/pages/base/fixed_area.html",
                    type = "redirect")})
    public String assignCustomers2FixedArea() {
        WebClient
                .create("http://localhost:8180/crm20/webservice/customerService/assignCustomers2FixedArea")
                .query("fixedAreaId", model.getId())
                .query("customerIds", customerIds).put(null);

        return SUCCESS;
    }
    // 完成快递员和定区的关联，还有快递员和上班时间的关联
    //快递员id
    private  Long courierId;
    //上班时间id
    private  Long takeTimeId;
    @Action(value="fixedAreaAction_associationCourierToFixedArea",results={@Result(name="success",location="/pages/base/fixed_area.html",type="redirect")})
    public String associationCourierToFixedArea(){
        fixedAreaService.associationCourierToFixedAreaAndTakeTimeToCourier(model.getId(),courierId,takeTimeId);
        return SUCCESS;
        
    }
    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }
    public void setTakeTimeId(Long takeTimeId) {
        this.takeTimeId = takeTimeId;
    }
    
    public void setCustomerIds(List<Long> customerIds) {
        this.customerIds = customerIds;
    }
}
  
