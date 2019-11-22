package com.tu.controllor;

import com.tu.entity.User;
import com.tu.mapper.UserMapper;
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

    @RequestMapping("/")
    public User fun(){
        return mapper.selectByPrimaryKey(1);
    }
}
