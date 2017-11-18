package com.itheima.bos.test;

import java.util.List;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.domain.base.Area;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.bos.dao.base.StandardRepository;
import com.itheima.bos.domain.base.Standard;

/**  
 * ClassName:StandardRepositoryTest <br/>  
 * Function:  <br/>  
 * Date:     Oct 31, 2017 9:07:31 AM <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class StandardRepositoryTest {
    @Autowired
    private StandardRepository standardRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Test
    public void testAdd(){
        Standard standard = new Standard();
        standard.setName("mlxg");
        standard.setMaxWeight(131);
        standardRepository.save(standard);
    }
    @Test
    public void test2(){
        Area sendAreaDB = areaRepository.
                findByProvinceAndCityAndDistrict("河北省",
                        "石家庄市", "新华区");
        System.out.println(sendAreaDB);
    }
    @Test
    public void testfindAll(){
        List<Standard> list = standardRepository.findAll();
        for (Standard standard : list) {
            System.out.println(standard);
        }
    }
   
    @Test
    public void testfindById(){
        Standard standard = standardRepository.findById((long) 2);
        System.out.println(standard);
    }
   @Test
   public void testUpdate(){
       Standard standard = new Standard();
       standard.setId((long) 2);
       standard.setName("悟空");
       standardRepository.save(standard);
   }
   
   @Test
   public void testDelete(){
       standardRepository.delete((long) 1);
   }
}
  
