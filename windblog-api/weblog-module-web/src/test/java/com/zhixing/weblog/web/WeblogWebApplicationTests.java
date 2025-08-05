package com.zhixing.weblog.web;

import com.zhixing.weblog.comm.domain.dos.UserDO;
import com.zhixing.weblog.comm.domain.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class WeblogWebApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    void insertTest() {
        UserDO userDO = UserDO.builder()
                .username("知性")
                .password("123456")
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        userMapper.insert(userDO);
    }

}
