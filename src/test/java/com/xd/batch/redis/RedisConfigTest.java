package com.xd.batch.redis;

import com.xd.batch.service.Test34Service;
import com.xd.batch.service.TestService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisConfigTest {

  /*  @Resource
    private RedisCache redisCache;*/

    @Autowired
    private TestService testService;

    @Autowired
    private Test34Service test34Service;

   /* @Test
    public void HashOperations() throws Exception{
        User userVo = new User();
        userVo.setUserName("jantent");
        userVo.setPassword("23");
        //redisUtil.hSet("1","2",userVo);
        redisCache.hSet("1","3U",userVo);
        User user = (User) redisCache.hGet("1", "3U");
        System.out.println("============hGet==================");
        System.out.println(user);
        System.out.println("============hmGet==================");
        Map<Object, Object> objectObjectMap = redisCache.hmGet("1");
        objectObjectMap.forEach((key,value)->{
            System.out.println(key + ":" + value);
        });
    }
    @Test
    public void testRedis(){
        User user =  (User) redisCache.get("name");
        System.out.println("name:"+user);
    }
    @Test
    public void add(){
        User user = new User();
        user.setUserName("xiaodai-1");
        user.setPassword("1992");
        user.setAge(22);
        redisCache.set("a-xiaodai", user);
    }

    @Test
    public void del(){
        redisCache.delKey("user");
    }*/


    @Test
    public void doUser(){

        //System.out.println(testService.doStudentProcess());

        try {
            System.out.println(testService.doUserProcess());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void doTest34(){


        try {
            System.out.println(test34Service.doStudentProcess());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
