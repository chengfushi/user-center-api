package com.chengfu.usercenterapi.service;

import com.chengfu.usercenterapi.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author 30362
* @description 针对表【user(用户信息表)】的数据库操作Service
* @createDate 2025-05-08 18:23:54
*/
public interface UserService extends IService<User> {

	/**
	 * @description:  用户注册逻辑
	 * @return: 新用户id
	 * @author Cheng Fu
	 */
	long userRegister(String userAccount,String userPassword,String checkPassword);
	
	/** 
	 * @description: 用户登录逻辑 
	 * @return: 用户脱敏后的信息
	 * @author Cheng Fu
	 */
	User userLogin(String userAccount, String userPassword, HttpServletRequest request);

	/*
		脱敏方法
		 */
	User getSafetyUser(User user);


	/*
	用户登出
	 */
	Integer userLogout(HttpServletRequest request);
}
