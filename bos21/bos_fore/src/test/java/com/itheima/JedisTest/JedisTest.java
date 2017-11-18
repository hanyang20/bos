package com.itheima.JedisTest;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**  
 * ClassName:JedisTest <br/>  
 * Function:  <br/>  
 * Date:     Nov 7, 2017 4:31:44 PM <br/>       
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class JedisTest {
        @Autowired
        private RedisTemplate<String, String> template;
        @Test
        public void test01() {
            // 添加数据
            template.opsForValue().set("name", "zhangsan");
        }
        @Test
        public void test02() {
            // 删除数据
            template.delete("name");
        }
        @Test
        public void test03() {
            // 添加数据,并指定过期时间
            /**
             * @param key
             * @param value
             * @param timeout : 超时时间
             * @param units : 时间单位
             */
            template.opsForValue().set("name", "zhangsan", 10, TimeUnit.SECONDS);
        }
    }

  
