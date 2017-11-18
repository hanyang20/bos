package com.itheima.bos.service.take_delivery.impl;

import com.itheima.bos.dao.base.AreaRepository;
import com.itheima.bos.dao.base.FixedAreaRepository;
import com.itheima.bos.dao.take_devlivery.OrderRepository;
import com.itheima.bos.dao.take_devlivery.WorkBillRepository;
import com.itheima.bos.domain.base.Area;
import com.itheima.bos.domain.base.Courier;
import com.itheima.bos.domain.base.FixedArea;
import com.itheima.bos.domain.base.SubArea;
import com.itheima.bos.domain.take_delivery.Order;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.bos.service.take_delivery.OrderService;
import com.itheima.crm.domain.Customer;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AreaRepository areaRepository;
    @Autowired
    private FixedAreaRepository fixedAreaRepository;
    @Autowired
    private WorkBillRepository workBillRepository;
    @Override
    public void save(Order order) {
        //之前是瞬时状态，持久状态order不能和瞬时状态一起保存
        Area sendArea = order.getSendArea();
        Area recArea = order.getRecArea();
        if(sendArea!=null){
            //根据寄件人省市区去查询是否存在此区域，若查到就为持久态然后去再去关联订单
            Area sendAreaDB = areaRepository.
                    findByProvinceAndCityAndDistrict(sendArea.getProvince(),
                    sendArea.getCity(), sendArea.getDistrict());
            order.setSendArea(sendAreaDB);
        }
        if(recArea!=null){
            //根据收件人省市区去查询是否存在此区域，若查到就为持久态然后去再去关联订单
            Area recAreaDB = areaRepository.findByProvinceAndCityAndDistrict(recArea.getProvince(),
                    recArea.getCity(), recArea.getDistrict());
            order.setRecArea(recAreaDB);
        }
        order.setOrderNum(RandomStringUtils.randomNumeric(32));
        order.setOrderTime(new Date());
        orderRepository.save(order);
        //取出寄件人的详细地址，去客户那里查询定区id，查到就自动分单，生成工单
        String sendAddress = order.getSendAddress();
        if(StringUtils.isNotBlank(sendAddress)) {
            Long fixedAreaId = WebClient
                    .create("http://localhost:8180/crm20/webservice/customerService/findFixedAreaIdByAddress")
                    .query("address", sendAddress)
                    .get(Long.class);
            if (fixedAreaId != null && fixedAreaId != 0) {
                FixedArea fixedArea = fixedAreaRepository.findOne(fixedAreaId);
                Set<Courier> couriers = fixedArea.getCouriers();
                Iterator<Courier> iterator = couriers.iterator();
                Courier courier = iterator.next();
                //订单关联快递员
                order.setCourier(courier);
                //生成工单，并保存
                findCourior(order, courier);
                return;
            }
        }
            //若没有查到，根据输入的分区关键字和辅助关键字，实现自动分单
            //先取出寄件人选择的区域
            Area sendAreaDB = order.getSendArea();
            if(sendAreaDB!=null){
                //从区域中取出所有的分区，遍历
                Set<SubArea> subareas = sendAreaDB.getSubareas();
                if(subareas!=null&&subareas.size()>0){
                    for (SubArea subarea: subareas) {
                        //从分区中取出关键字和辅助关键字
                        String keyWords = subarea.getKeyWords();
                        String assistKeyWords = subarea.getAssistKeyWords();
                        //如果寄件人输入的寄件地址中含有该分区关键字或辅助关键字
                        if(sendAddress.contains(keyWords)
                                || sendAddress.contains(assistKeyWords)) {
                            //然后取出该定区，然后取出定区的所有快递员
                            FixedArea fixedArea = subarea.getFixedArea();
                            // 如果是真实的业务,需要判断快递员的收派标准和工作时间
                            Set<Courier> couriers = fixedArea.getCouriers();
                            Iterator<Courier> iterator = couriers.iterator();
                            Courier courier = iterator.next();
                            //订单关联快递员
                            order.setCourier(courier);
                            //生成工单，并保存
                            findCourior(order,courier);
                            return;
                        }
                    }
                }
            }
            //人工分单
            order.setOrderType("人工分单");
        }




    void findCourior(Order order,Courier courier){
    //生成工单
    WorkBill workBill=new WorkBill();
    workBill.setAttachbilltimes(0);
    workBill.setBuildtime(new Date());
    workBill.setCourier(courier);
    workBill.setOrder(order);
    workBill.setPickstate("已通知");
    workBill.setRemark(order.getRemark());
    workBill.setSmsNumber("123");
    workBill.setType("新单");
    order.setOrderType("自动分单");
        // 发送工单信息给快递员,此处打印日志进行模拟
        System.out.println("工单信息:请到" + order.getSendAddress() + "取件,客户电话:" + order.getSendMobile());
    workBillRepository.save(workBill);
}
}