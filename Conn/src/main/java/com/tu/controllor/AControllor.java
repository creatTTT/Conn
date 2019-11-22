package com.tu.controllor;

import com.tu.entity.User;
import com.tu.mapper.UserMapper;
import com.tu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public User fun(){
        return userService.findUserById(1);
    }

    @RequestMapping("/a")
    public String test(){
        return userService.text();
    }

}
