package com.itheima.crm.service;
/**
 * ClassName:CustomerService <br/>
 * Function: <br/>
 * Date: Nov 5, 2017 3:09:17 PM <br/>
 */

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.itheima.crm.domain.Customer;

@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface CustomerService {

    @GET
    @Path("/customer")
    public List<Customer> findAll();

    // 查找未关联到定区的客户数据
    @GET
    @Path("/findUnAssociatedCustomers")
    public List<Customer> findUnAssociatedCustomers();

    // 查找关联到指定定区的客户数据
    @GET
    @Path("/findCustomersAssociated2FixedArea")
    public List<Customer> findCustomersAssociated2FixedArea(
            @QueryParam("fixedAreaId") String fixedAreaId);

    @PUT
    @Path("/assignCustomers2FixedArea")
    public void assignCustomers2FixedArea(
            @QueryParam("fixedAreaId") String fixedAreaId,
            @QueryParam("customerIds") List<Long> customerIds);
    @POST
    @Path("/save")
    public void save(Customer model);

 // 分页查找关联到指定定区的客户数据
    @GET
    @Path("/findCustomersAssociated2FixedArea02")
    public Page<Customer> findCustomersAssociated2FixedArea02(@QueryParam("specification") Specification<Customer> specification,
            @QueryParam("pageable") Pageable pageable);
   //根据手机号码查找用户
    @GET
    @Path("/findByTelephone")
    public Customer findByTelephone(
            @QueryParam("telephone") String telephone);
    //更改状态码
    @PUT
    @Path("/updateType")
    public void updateType(@QueryParam("telephone") String telephone);
    
    @GET
    @Path("/findCustomerByNameAndPwd")
    public Customer findCustomerByNameAndPwd(
            @QueryParam("telephone") String telephone,
            @QueryParam("password") String password);
    @GET
    @Path("/findFixedAreaIdByAddress")
    public Long findFixedAreaIdByAddress(
            @QueryParam("address") String address);
}
