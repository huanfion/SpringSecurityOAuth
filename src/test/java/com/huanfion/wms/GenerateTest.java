package com.huanfion.wms;

import com.huanfion.wms.entity.User;
import com.huanfion.wms.generator.PostgreSqlGenerator;
import com.huanfion.wms.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateTest {

    @Autowired
    @Qualifier("userServiceImpl")
    private IUserService userService;
    @Test
    public void generate(){
        PostgreSqlGenerator postgreSqlGenerator = new PostgreSqlGenerator();
        postgreSqlGenerator.generator("tb_user","tb","",true,"huanfion");
    }
    @Test
    public void queryUser(){
        User byId = userService.getById(335);
        System.out.println(byId);
    }


}
