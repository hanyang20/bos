package com.itheima.bos.dao.take_devlivery;

import com.itheima.bos.domain.take_delivery.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository  extends JpaRepository<Order,Long>{
}
