package com.itheima.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.itheima.crm.domain.Customer;

/**
 * ClassName:CustomerRepository <br/>
 * Function: <br/>
 * Date: Nov 5, 2017 3:08:48 PM <br/>
 */
public interface CustomerRepository extends JpaRepository<Customer, Long>,JpaSpecificationExecutor<Customer> {
    // 查找未关联到定区的客户数据
    List<Customer> findByFixedAreaIdIsNull();

    // 查找关联到指定定区的客户数据
    List<Customer> findByfixedAreaId(String fixedAreaId);

    // 把关联到指定定区ID的客户进行解绑
    @Modifying
    @Query("update Customer set fixedAreaId = null where fixedAreaId = ?")
    void setFixedAreaNullByFixedAreaId(String fixedAreaId);

    //关联客户到指定定区
    @Modifying
    @Query("update Customer set fixedAreaId = ? where id = ?")
    void updateCustomers2FixedArea(String fixedAreaId, Long id);
    
    //根据手机号码查找用户
    Customer findByTelephone(String telephone);
    //更改状态码
    @Modifying
    @Query("update Customer set type = 1 where telephone = ?")
    void updateType(String telephone);
    //登录
    Customer findByTelephoneAndPassword(String telephone,String password);

    //根据地址信息查找返回定区id
    @Query("select fixedAreaId from Customer where address = ?")
    Long findFixedAreaIdByAddress(String address);


}
