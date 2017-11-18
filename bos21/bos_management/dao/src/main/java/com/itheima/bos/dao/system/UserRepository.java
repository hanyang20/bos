package com.itheima.bos.dao.base;

import com.itheima.bos.domain.system.User;
import com.itheima.crm.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/*
*
 * ClassName:CustomerRepository <br/>
 * Function: <br/>
 * Date: Nov 5, 2017 3:08:48 PM <br/>
*/




public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User> {


    //shiro框架
    User findByUsername(String username);
}
