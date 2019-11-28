package com.tu.controllor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tu.annotation.NeedLogined;
import com.tu.entity.User;
import com.tu.mapper.UserMapper;
import com.tu.service.TokenService;
import com.tu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Administrator on 2019/11/21 0021.
 */
@RestController
public class AControllor {


    @Autowired
    UserMapper mapper;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;

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

    @RequestMapping("/login")
    public Object login(){
        JSONObject jsonObject=new JSONObject();
        String userForBase=userService.findUserById(1);
        User user= JSON.parseObject(userForBase,User.class);
        if(user==null){
            jsonObject.put("message","登录失败,用户不存在");
            return jsonObject;
        }else {
            String token = tokenService.getToken(user);
            jsonObject.put("token", token);
            jsonObject.put("user", userForBase);
            return jsonObject;
        }
    }

    @NeedLogined
    @GetMapping("/getMessage")
    public String getMessage(){
        return "你已通过验证";
    }

}
