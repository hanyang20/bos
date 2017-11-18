package com.itheima.bos.service.take_delivery;

import com.itheima.bos.domain.take_delivery.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public interface OrderService {
    @POST
    @Path("/save")
    public void save(Order order);
}
