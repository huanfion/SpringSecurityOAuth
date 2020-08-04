package com.huanfion.wms.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * BCryptPasswordEncoder 测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBCrypt {
    @Test
    public void testBCrypt() {
        String hashpw= BCrypt.hashpw("123456",BCrypt.gensalt());
        System.out.println(hashpw);
        boolean checkpw = BCrypt.checkpw("123456", hashpw);
        System.out.println(checkpw);
    }
}
