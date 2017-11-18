package com.itheima.bos.service.jobs;

import com.itheima.bos.dao.take_devlivery.WorkBillRepository;
import com.itheima.bos.domain.take_delivery.WorkBill;
import com.itheima.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkBillMailJob {
    @Autowired
    private WorkBillRepository workBillRepository;
    public void sendMail(){
        List<WorkBill> list = workBillRepository.findAll();
        String emailBody="编号\t工单类型\t下单时间\t追单次数\t快递员<br/>";
        for (WorkBill workBill:list){
            emailBody+=workBill.getId()
                    +"\t"+workBill.getType()
                    +"\t"+workBill.getBuildtime()
                    +"\t"+workBill.getAttachbilltimes()
                    +"\t"+workBill.getCourier().getName()+"<br/>";
        }

        MailUtils.sendMail("lucky@itheima20.com","工单信息",emailBody);
    }
}
