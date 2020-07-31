package com.huanfion.wms;

import com.huanfion.wms.generator.PostgreSqlGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenerateTest {

    @Test
    public void generate(){
        PostgreSqlGenerator postgreSqlGenerator = new PostgreSqlGenerator();
        postgreSqlGenerator.generator("tb_user","tb","",true,"huanfion");
    }
}
