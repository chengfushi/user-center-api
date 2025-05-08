package com.chengfu.usercenter.service;

import com.chengfu.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
