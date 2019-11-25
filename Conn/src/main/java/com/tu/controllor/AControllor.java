package com.tu.controllor;

import com.tu.entity.User;
import com.tu.mapper.UserMapper;
import com.tu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2019/11/21 0021.
 */
@RestController
public class AControllor {


    @Autowired
    UserMapper mapper;
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String fun(){
        return userService.findUserById(1);
    }

    @RequestMapping("/abc")
    public String test(){
        System.out.println("==============");
        return userService.text();
    }


    /**
     * 查询出一条数据并且添加到缓存
     *
     * @param userId
     * @return
     */
    @RequestMapping("/getUser")
    @Cacheable(value = "userCache")
    public User getPrud(@RequestParam(required = true) String userId) {
        System.out.println("如果没有缓存，就会调用下面方法;如果有缓存，则直接输出，不会输出此段话");
        return userService.getUser(Integer.parseInt(userId));
    }
    /**
     * 删除一个缓存
     *
     * @param userId
     * @return
     */
    @RequestMapping(value = "/deleteUser")
    @CacheEvict("userCache")
    public String deleteUser(@RequestParam(required = true) String userId) {
        return "删除成功";
    }

}
