package com.tu.service;

import com.alibaba.fastjson.JSON;
import com.tu.entity.User;
import com.tu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2019/11/22 0022.
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public String text(){

        return stringRedisTemplate.opsForValue().get("User_2");
    }

    /**
     * 获取用户策略：先从缓存中获取用户，没有则取数据表中 数据，再将数据写入缓存
     */
    public String findUserById(int id) {
        String key = "user_" + id;

        boolean hasKey = stringRedisTemplate.hasKey(key);
        if (hasKey) {
            long start = System.currentTimeMillis();
            String user = stringRedisTemplate.opsForValue().get(key);
            System.out.println("==========从缓存中获得数据=========");
            long end = System.currentTimeMillis();
            System.out.println("查询redis花费的时间是:" + (end - start)+"ms");
            return user;
        } else {
            long start = System.currentTimeMillis();
            User user = userMapper.selectByPrimaryKey(id);
            System.out.println("==========从数据表中获得数据=========");
            System.out.println("username: "+user.getName());
            // 写入缓存
            stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(user),5,TimeUnit.MINUTES);
            System.out.println(JSON.toJSON(user)+"-----------------");
            long end = System.currentTimeMillis();
            System.out.println("查询mysql花费的时间是:" + (end - start)+"ms");
            User user1=JSON.parseObject(JSON.toJSONString(user),User.class);
            return user1.toString();
        }

    }

    public User getUser(int userId) {
        System.out.println("执行此方法，说明没有缓存，如果没有走到这里，就说明缓存成功了");
        User user = new User();
        user.setuId(999);
        user.setName("aaa");
        user.setPhonenum("123456789");
        return user;
    }

    public User getUser2(int userId) {
        System.out.println("执行此方法，说明没有缓存，如果没有走到这里，就说明缓存成功了");
        User user = new User();
        user.setuId(777);
        user.setName("bbb");
        user.setPhonenum("123456789");
        return user;
    }

}
