package com.itheima.bos.web.action.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.CDATA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.Standard;
import com.itheima.bos.service.base.CourierService;
import com.itheima.bos.web.common.BaseAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**  
 * ClassName:CourierAction <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:49:37 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class CourierAction extends BaseAction<Courier>{
    @Autowired
    private CourierService courierService;
   
    @Action(value="courierAction_save",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String save() {
        courierService.save(model);
        return SUCCESS;
    }
   
    @Action(value="courierAction_pageQuery")
    public String pageQuery() throws IOException {
        Specification<Courier> specification=new Specification<Courier>() {

            @Override
            public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
                    CriteriaBuilder cb) {
                String courierNum = model.getCourierNum();
                String company = model.getCompany();
                String type = model.getType();
                Standard standard = model.getStandard();
                
                List<Predicate> list=new ArrayList<>();
                //公司编号
                if(StringUtils.isNotBlank(courierNum)){
                    Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courierNum);
                    list.add(p1);
                }
                //公司模糊查询
                if(StringUtils.isNotBlank(company)){
                    // 如果公司不为空,就构造一个公司的模糊查询
                    // where company like ?
                    Predicate p2 = cb.like(root.get("company").as(String.class), "%"+company+"%");
                    list.add(p2);
                }
                if(StringUtils.isNotBlank(type)){
                    // 如果类型不为空,就构造一个类型的等值查询
                    // where type = ?
                    Predicate p3 = cb.equal(root.get("type").as(String.class), type);
                    list.add(p3);
                }
                if(standard!=null){
                    if(StringUtils.isNotBlank(standard.getName())){
                        // 因为Standard是对象,也就是Courier的关联对象,所以需要先获取关联对象,再去执行查询
                        Join<Object, Object> join = root.join("standard");
                        Predicate p4 = cb.equal(join.get("name").as(String.class), standard.getName());
                        list.add(p4);
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
        Pageable pageable=new PageRequest(page-1, rows);
        Page<Courier> page=courierService.pageQuery(specification,pageable);
        java2Json(page, new String[]{"fixedAreas","takeTime"});
        return NONE;
    }
    private String ids;
    @Action(value="courierAction_deleteBatch",results={@Result(name="success",location="/pages/base/courier.html",type="redirect")})
    public String deleteBatch() {
        courierService.deleteBatch(ids);
        return SUCCESS;
    }
    @Action(value="courierAction_findAll")
   public String findAll(){
        List<Courier> list=courierService.findAll();
        list2json(list, new String[]{"fixedAreas","takeTime"});
       return NONE;
   }
    
    
    
    public void setIds(String ids) {
        this.ids = ids;
    }
    
}
  
