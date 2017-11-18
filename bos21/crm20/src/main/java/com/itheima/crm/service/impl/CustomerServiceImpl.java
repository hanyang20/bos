package com.itheima.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.crm.dao.CustomerRepository;
import com.itheima.crm.domain.Customer;
import com.itheima.crm.service.CustomerService;

/**
 * ClassName:CustomerServiceImpl <br/>
 * Function: <br/>
 * Date: Nov 5, 2017 3:09:31 PM <br/>
 */
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {

        return customerRepository.findAll();
    }

    // 查找未关联到定区的客户数据
    @Override
    public List<Customer> findUnAssociatedCustomers() {

        return customerRepository.findByFixedAreaIdIsNull();
    }

    // 查找关联到指定定区的客户数据
    @Override
    public List<Customer> findCustomersAssociated2FixedArea(
            String fixedAreaId) {

        return customerRepository.findByfixedAreaId(fixedAreaId);
    }

    // 关联客户到指定定区
    @Override
    public void assignCustomers2FixedArea(String fixedAreaId,
                                          List<Long> customerIds) {

        // 和指定的定区Id关联的客户进行解绑操作
        customerRepository.setFixedAreaNullByFixedAreaId(fixedAreaId);
        // 再把指定的客户绑定到指定的定区
        if (customerIds != null && customerIds.size() > 0) {
            for (Long id : customerIds) {
                customerRepository.updateCustomers2FixedArea(fixedAreaId, id);
            }
        }

    }

    @Override
    public void save(Customer model) {
        customerRepository.save(model);
    }


    @Override
    public Page<Customer> findCustomersAssociated2FixedArea02(
            Specification<Customer> specification, Pageable pageable) {
        return customerRepository.findAll(specification, pageable);
    }
    //根据手机号码查找用户
    @Override
    public Customer findByTelephone(String telephone) {
        return customerRepository.findByTelephone(telephone);
    }

    @Override
    public void updateType(String telephone) {

        customerRepository.updateType(telephone);

    }

    @Override
    public Customer findCustomerByNameAndPwd(String telephone, String password) {
        return customerRepository.findByTelephoneAndPassword(telephone, password);
    }

    @Override
    public Long findFixedAreaIdByAddress(String address) {
        return customerRepository.findFixedAreaIdByAddress(address);
    }
}
