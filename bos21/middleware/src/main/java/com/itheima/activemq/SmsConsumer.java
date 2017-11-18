package com.itheima.activemq;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.stereotype.Component;

/**
 * ClassName:SmsConsumer <br/>
 * Function: 短信的消费者 <br/>
 * Date: Nov 12, 2017 5:07:01 PM <br/>
 */
@Component
public class SmsConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage = (MapMessage) message;
        try {
            String telephone = mapMessage.getString("telephone");
            String msg = mapMessage.getString("msg");
            // SmsUtils.sendSmsByWebService(model.getTelephone(), msg);
            System.out.println(telephone + "===" + msg);
        } catch (JMSException e) {

            e.printStackTrace();

        }

    }

}
