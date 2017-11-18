package com.itheima.cxftest;
/**
 * ClassName:CxfTest <br/>
 * Function: <br/>
 * Date: Nov 5, 2017 3:17:07 PM <br/>
 */

import java.util.Collection;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.itheima.crm.domain.Customer;

public class CXFTest {

    @Test
    public void test1() {
        Collection<? extends Customer> collection = WebClient
                .create("http://localhost:8180/crm20/webservice/customerService/customer")
                .accept(MediaType.APPLICATION_JSON)// 指定对方传过来的数据格式
                .type(MediaType.APPLICATION_JSON)// 指定传输给对方的数据格式
                .getCollection(Customer.class);

        for (Customer customer : collection) {
            System.out.println(customer);
        }
    }
}
