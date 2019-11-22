package com.tu.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2019/11/22 0022.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    @Autowired
    OrderMapper mapper;

    @Test
    public void testSelectByPrimaryKey() throws Exception {
        System.out.println(mapper.selectByPrimaryKey(1).toString());
    }
}