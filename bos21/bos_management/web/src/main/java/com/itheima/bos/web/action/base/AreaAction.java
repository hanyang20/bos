package com.itheima.bos.web.action.base;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
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
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.service.base.AreaService;
import com.itheima.bos.web.common.BaseAction;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.itheima.utils.PinYin4jUtils;
/**  
 * ClassName:AreaAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 2, 2017 8:35:51 PM <br/>       
 */
@Namespace("/")
@ParentPackage("struts-default")
@Controller
@Scope("prototype")
public class AreaAction extends BaseAction<Area>{
    private File file;
    @Autowired
    private AreaService areaService;
    @Action(value="areaAction_importXls",results={@Result(name="success",location="/pages/base/area.html",type="redirect")})
    public String importXls() throws Exception {
        
        HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
        List<Area> list=new ArrayList<>();
        for (Row row : sheetAt) {
            if(row.getRowNum()==0){
                continue;
            }
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();
            Area area=new Area(province, city, district, postcode);
           
            province=province.substring(0, province.length()-1);
            city=city.substring(0, city.length()-1);
            district=district.substring(0, district.length()-1);
            String sum=province+city+district;
            String[] strings = PinYin4jUtils.getHeadByString(sum);
            String shortcode = StringUtils.join(strings);
            String citycode = PinYin4jUtils.hanziToPinyin(city, "").toUpperCase();
            area.setCitycode(citycode);
            area.setShortcode(shortcode);
            list.add(area);
        }
        areaService.importXls(list);
        return SUCCESS;
    }
    
    @Action(value = "areaAction_pageQuery")
    public String pageQuery() throws IOException {
        Specification<Area> specification=new Specification<Area>() {

            @Override
            public Predicate toPredicate(Root<Area> root,
                    CriteriaQuery<?> query, CriteriaBuilder cb) {
                  String province = model.getProvince();
                  String city = model.getCity();
                  String district = model.getDistrict();
                  List<Predicate> list=new ArrayList<>();
                  if(StringUtils.isNotBlank(province)){
                      Predicate p1 = cb.like(root.get("province").as(String.class), "%"+province+"%");
                      list.add(p1);
                  }
                  if(StringUtils.isNotBlank(city)){
                      Predicate p2 = cb.like(root.get("city").as(String.class), "%"+city+"%");
                      list.add(p2);
                  }
                  if(StringUtils.isNotBlank(district)){
                      Predicate p3 = cb.like(root.get("district").as(String.class), "%"+district+"%");
                      list.add(p3);
                  }
                  if(list.size()==0){
                      return null;
                  }
                  Predicate[] arr=new Predicate[list.size()];
                  list.toArray(arr);
                return cb.and(arr);
            }};
        Pageable pageable=new PageRequest(page-1 ,rows);
        Page<Area> page=areaService.pageQuery(specification,pageable);
       java2Json(page, new String[]{"subareas"});
        return NONE;
    }
 // 使用属性驱动获取用户输入的数据
    private String q;
    public void setQ(String q) {
        this.q = q;
    }
    // 添加删除分区弹出框中查询区域数据
    @Action("areaAction_findAll")
    public String findAll() throws IOException {
        List<Area> list = null;
        // 如果用户输入了查询条件,就根据条件进行查询
        if (StringUtils.isNotEmpty(q)) {
            list = areaService.findQ(q);
        } else {
            // 如果用户没有输入查询条件,就查询所有
            list = areaService.findAll();
        }
        list2json(list,new String[]{"subareas"});
        return NONE;
    }
   
    
    
    public void setFile(File file) {
        this.file = file;
    }

}
  
