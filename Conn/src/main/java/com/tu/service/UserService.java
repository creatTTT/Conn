package com.tu.service;

import com.tu.entity.User;
import com.tu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
    RedisTemplate redisTemplate;

    public String text(){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.get("user_2");
    }




    /**
     * 获取用户策略：先从缓存中获取用户，没有则取数据表中 数据，再将数据写入缓存
     */
    public User findUserById(int id) {
        String key = "user_" + id;

        ValueOperations<String, User> operations = redisTemplate.opsForValue();

        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            long start = System.currentTimeMillis();
            User user = operations.get(key);
            System.out.println("==========从缓存中获得数据=========");
            System.out.println("username: "+user.getName());
            long end = System.currentTimeMillis();
            System.out.println("查询redis花费的时间是:" + (end - start)+"ms");
            System.out.println("==============================");
            return user;
        } else {
            long start = System.currentTimeMillis();
            User user = userMapper.selectByPrimaryKey(id);
            System.out.println("==========从数据表中获得数据=========");
            System.out.println("username: "+user.getName());
            // 写入缓存
            operations.set(key, user, 5, TimeUnit.MINUTES);
            long end = System.currentTimeMillis();
            System.out.println("查询mysql花费的时间是:" + (end - start)+"ms");
            System.out.println("==============================");
            return user;
        }

    }

    /**
     * 更新用户策略：先更新数据表，成功之后，删除原来的缓存，再更新缓存
     */
    public int updateUser(User user) {
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        int result = userMapper.updateByPrimaryKey(user);
        if (result != 0) {
            String key = "user_" + user.getuId();
            boolean haskey = redisTemplate.hasKey(key);
            if (haskey) {
                redisTemplate.delete(key);
                System.out.println("删除缓存中的key=========>" + key);
            }
            // 再将更新后的数据加入缓存
            User userNew = userMapper.selectByPrimaryKey(user.getuId());
            if (userNew != null) {
                operations.set(key, userNew, 3, TimeUnit.HOURS);
            }
        }
        return result;
    }

    /**
     * 删除用户策略：删除数据表中数据，然后删除缓存
     */
    public int deleteUserById(int id) {
        int result = userMapper.deleteByPrimaryKey(id);
        String key = "user_" + id;
        if (result != 0) {
            boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey) {
                redisTemplate.delete(key);
                System.out.println("删除了缓存中的key:" + key);
            }
        }
        return result;
    }
}
