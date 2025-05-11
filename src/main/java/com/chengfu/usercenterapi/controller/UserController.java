package com.chengfu.usercenterapi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengfu.usercenterapi.common.BaseResponse;
import com.chengfu.usercenterapi.common.BusinessException;
import com.chengfu.usercenterapi.common.ErrorCode;
import com.chengfu.usercenterapi.model.domain.User;
import com.chengfu.usercenterapi.model.request.UserLoginRequest;
import com.chengfu.usercenterapi.model.request.UserRegisterRequest;
import com.chengfu.usercenterapi.service.UserService;
import com.chengfu.usercenterapi.utils.ResultUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.chengfu.usercenterapi.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author Cheng Fu
 * @description: TODO
 * @date 2025/5/9 9:59
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	@PostMapping("/register")
	public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
		if (userRegisterRequest == null) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}
		String userAccount = userRegisterRequest.getUserAccount();
		String userPassword = userRegisterRequest.getUserPassword();
		String checkPassword = userRegisterRequest.getCheckPassword();

		if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
			throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
		}


		long userRegister = userService.userRegister(userAccount, userPassword, checkPassword);

		return ResultUtils.success(userRegister);

	}

	@PostMapping("/login")
	public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
		if (userLoginRequest == null) {
			throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
		}

		String userAccount = userLoginRequest.getUserAccount();
		String userPassword = userLoginRequest.getUserPassword();
		if (StringUtils.isAnyBlank(userAccount, userPassword)) {
			throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
		}

		User user = userService.userLogin(userAccount, userPassword, request);

		return ResultUtils.success(user);

	}


	/*
	管理员通过用户名查询用户
	 */
	@GetMapping("/search")
	public BaseResponse<List<User>> searchUser(String userName,HttpServletRequest request) {

		//判断当前用户的身份
		if (!isAdmin(request)){
			//返回权限错误信息
			throw new BusinessException(ErrorCode.NO_AUTH);

		}


		QueryWrapper<User> queryWrapper = new QueryWrapper<>();

		//校验参数
		if (StringUtils.isNoneBlank(userName)) {
			queryWrapper.like("user_name", userName);
		}

		List<User> userList =  userService.list(queryWrapper);

		List<User> searchResult = userList.stream().map(user -> userService.getSafetyUser(user)).toList();

		return ResultUtils.success(searchResult);
	}

	/*
	管理员删除用户
	 */
	@DeleteMapping ("/delete")
	public boolean deleteUser(@RequestBody long userID,HttpServletRequest request){
		//判断用户当前的身份
		if (!isAdmin(request)){
			throw new BusinessException(ErrorCode.NO_AUTH);
		}

		//创建一个查询条件
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();

		//删除用户(逻辑删除)
		return userService.removeById(userID);
	}

	/*
	确认权限
	 */
	public boolean isAdmin(HttpServletRequest request){
		//获取用户的登陆态
		Object  userObj = request.getSession().getAttribute(USER_LOGIN_STATE);

		User user = (User) userObj;


		//获取当前的权限
		if (user.getUserRole() != 1) {
			return false;
		}

		return true;
	}

	/*
	用户注销
	 */
	@PostMapping("/logout")
	public Integer userLogout(HttpServletRequest request) {
		if (request == null) {
			new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
		}

		return userService.userLogout(request);
	}

}
