package com.chengfu.usercenterapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

@SpringBootTest
class UserCenterApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testMd5() {
		String password = "123456";
		String newPassword = DigestUtils.md5DigestAsHex(password.getBytes());
		System.out.println(newPassword);
	}

}
