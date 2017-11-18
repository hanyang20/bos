package com.itheima.bos.fore.web.action;


import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;

import com.itheima.crm.domain.Customer;
import com.itheima.utils.MailUtils;
import com.itheima.utils.SmsUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**  
 * ClassName:CustomerAction <br/>  
 * Function:  <br/>  
 * Date:     Nov 6, 2017 9:57:23 PM <br/>       
 */
@Namespace("/")
@ParentPackage("json-default")
@Controller
@Scope("prototype")
public class CustomerAction extends ActionSupport implements ModelDriven<Customer>{
     private Customer model=new Customer();
     private String checkcode;
    // 注入Redis模版类
     @Autowired
     private RedisTemplate<String, String> redisTemplate;

     //注入jms服务模板
    @Autowired
     private JmsTemplate jmsTemplate;
    @Override
    public Customer getModel() {
        return model;
    }
    @Action(value="customerAction_sendSMS",results = {@Result(name = "success", type = "json")})
    public String sendSMS() throws Exception{
        Customer customer = WebClient.create("http://localhost:8180/crm20/webservice/customerService/findByTelephone")
                .query("telephone", model.getTelephone())
                .type(MediaType.APPLICATION_JSON)
                .get(Customer.class);
        final Map<String, String> map=new HashMap<>();
        if(customer!=null){
            map.put("result", "telephoneexsit");
        }else{
        String code = RandomStringUtils.randomNumeric(4);
        System.out.println("code:"+code);
        ServletActionContext.getRequest().getSession().setAttribute(model.getTelephone(), code);
        final String msg = "尊敬的客户你好，您本次获取的验证码为：" + code;
//        SmsUtils.sendSmsByWebService(model.getTelephone(), msg);
            jmsTemplate.send("sms", new MessageCreator() {
                @Override
                public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setString("telephone",model.getTelephone());
                    mapMessage.setString("msg",msg);

                    return mapMessage;
                }
            });
        map.put("result", "ok");

        System.out.print("hhh");
        }
        ActionContext.getContext().getValueStack().push(map);
        return SUCCESS;
    }
    //注册
    @Action(value="customerAction_regist",results={@Result(name = "success", location = "/signup-success.html",
            type = "redirect"),
      @Result(name = "error", location = "/signup-fail.html",
            type = "redirect")})
    public String regist(){
           String code = (String) ServletActionContext.getRequest().getSession().getAttribute(model.getTelephone());
         if(StringUtils.isNotBlank(code) && StringUtils.isNotBlank(checkcode) && code.equals(checkcode)){
            WebClient.create("http://localhost:8180/crm20/webservice/customerService/save")
            .type(MediaType.APPLICATION_JSON)
            .post(model);
           // 生成验证码
            String activeCode = RandomStringUtils.randomNumeric(30);
            // 将验证码存入Redis
            redisTemplate.opsForValue().set(model.getTelephone(), activeCode, 1,
                    TimeUnit.DAYS);
            // 拼接邮件内容
            String emailBody = "感谢您注册速运快递 ,请您在24小时内点击下面的链接激活您的帐号<br><a href='http://localhost:8280/bos_fore/customerAction_active.action?telephone="
                    + model.getTelephone() + "&activeCode=" + activeCode + "'>激活帐号</a>";
            // 发送激活邮件
            MailUtils.sendMail(model.getEmail(), "激活邮件", emailBody);
            return SUCCESS;
        }
        return ERROR;
    } 
    //激活
    private String activeCode;
    @Action(value="customerAction_active",results={
            @Result(name="success",location="/active_success.html",type="redirect"),
            @Result(name="error",location="/active_error.html",type="redirect"),
            @Result(name="actived",location="/active_actived.html",type="redirect")})
    public String active(){
        String telephone=model.getTelephone();
        if(StringUtils.isNotBlank(telephone)){
            String redisCode = redisTemplate.opsForValue().get(telephone);
            if (StringUtils.isNotBlank(activeCode)
                    && StringUtils.isNotBlank(redisCode)
                    &&activeCode.equals(redisCode)) {
                //判断用户是否已经激活，先去查询
                Customer customer = WebClient.create("http://localhost:8180/crm20/webservice/customerService/findByTelephone")
                .query("telephone", telephone)
                .type(MediaType.APPLICATION_JSON)
                .get(Customer.class);
                if(customer!=null){
                    if(customer.getType()!=null&&customer.getType()==1){
                        //已经完成注册
                        return "actived";
                    }
                }
                //激活码改变逻辑
                WebClient.create("http://localhost:8180/crm20/webservice/customerService/updateType")
                .query("telephone", telephone)
                .type(MediaType.APPLICATION_JSON)
                .put(null);
                // 删除Redis中的数据
                redisTemplate.delete(model.getTelephone());
                return SUCCESS;
            }
        }
        
        return ERROR;
        
    }

    @Action(value="customerAction_login",results={@Result(name="success",location="/index.html",type="redirect"),
            @Result(name="login",location="/login.html",type="redirect")})
    public String login(){
        String serverCode = (String) ServletActionContext.getRequest().getSession().getAttribute("validateCode");
        if (StringUtils.isNotBlank(serverCode)
                && StringUtils.isNotBlank(checkcode)
                && checkcode.equals(serverCode)) {
            //去查询用户
            Customer customer = WebClient.create("http://localhost:8180/crm20/webservice/customerService/findCustomerByNameAndPwd")
                    .query("telephone", model.getTelephone())
                    .query("password", model.getPassword())
                    .type(MediaType.APPLICATION_JSON)
                    .get(Customer.class);
            if(customer!=null){
                ServletActionContext.getRequest().getSession().setAttribute("customer", customer);
                return SUCCESS;
            }
        }
        return LOGIN;

    }
    
    
    
    
    
    
    
    
    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }
    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }
}
  
