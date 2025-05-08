package com.chengfu.usercenter.service;
import java.util.Date;

import com.chengfu.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description: 测试userService
 * @author Cheng Fu
 * @date:  18:31
 */
@SpringBootTest

public class UserServiceTest {

	@Resource
	private UserService userService;

	@Test
	public void testAddUser() {
		User user = new User();
		user.setUserName("testChengFu");
		user.setAvatarUrl("");
		user.setGender(0);
		user.setLoginAccount("");
		user.setUserPassword("");
		user.setPhone("");
		user.setEmail("");
		user.setUserStatus(0);
		user.setCreateTime(new Date());
		user.setUpdateTime(new Date());
		user.setIsDelete(0);


		boolean result = userService.save(user);
		System.out.println(user.getId());

		assertTrue(result);

	}

	@Test
	void userRegister() {
		String userAccount = "testChengFu1";
		String password = "12345678";
		String confirmPassword = "12345678";

		long result = userService.userRegister(userAccount, password, confirmPassword);
		assertTrue(result > 0);
	}
}