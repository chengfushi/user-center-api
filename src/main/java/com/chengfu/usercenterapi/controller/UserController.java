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
//@CrossOrigin(origins = {"https://localhost:3000"},allowCredentials = "true")
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
			return null;
		}


		long userRegister = userService.userRegister(userAccount, userPassword, checkPassword);

		return ResultUtils.success(userRegister);

	}

	@PostMapping("/login")
	public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
		if (userLoginRequest == null) {
			return ResultUtils.error(ErrorCode.PARAMS_ERROR);
		}

		String userAccount = userLoginRequest.getUserAccount();
		String userPassword = userLoginRequest.getUserPassword();
		if (StringUtils.isAnyBlank(userAccount, userPassword)) {
			return ResultUtils.error(ErrorCode.PARAMS_ERROR);
		}

		User user = userService.userLogin(userAccount, userPassword, request);

		return ResultUtils.success(user);

	}

	/*
用户注销
 */
	@PostMapping("/logout")
	public BaseResponse<Integer> userLogout(HttpServletRequest request) {
		if (request == null) {
			new BusinessException(ErrorCode.PARAMS_NULL_ERROR);
		}

		Integer result = userService.userLogout(request);
		return ResultUtils.success(result);
	}




	@GetMapping("/current")
	public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
		Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
		User currentUser = (User) userObj;
		if (currentUser == null) {
			return ResultUtils.error(ErrorCode.NOT_LOGIN, "用户未登录");
		}
		long userId = currentUser.getId();
		// TODO 校验用户是否合法
		User user = userService.getById(userId);
		User safetyUser = userService.getSafetyUser(user);
		return ResultUtils.success(safetyUser);
	}


	/*
	管理员通过用户名查询用户
	 */
	@GetMapping("/search")
	public BaseResponse<List<User>> searchUsers(String userName, HttpServletRequest request) {
		if (!isAdmin(request)) {
			return ResultUtils.error(ErrorCode.NO_AUTH,"无管理员权限");
		}
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		if (StringUtils.isNotBlank(userName)) {
			queryWrapper.like("user_name", userName);
		}
		List<User> userList = userService.list(queryWrapper);
		List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
		return ResultUtils.success(list);
	}


	/*
	管理员删除用户
	 */
	@DeleteMapping ("/delete")
	public BaseResponse<Boolean> deleteUser(@RequestBody long id,HttpServletRequest request){
		//判断用户当前的身份
		if (!isAdmin(request)){
			return ResultUtils.error(ErrorCode.NO_AUTH,"无权限");
		}

		if (id <= 0){
			throw new BusinessException(ErrorCode.PARAMS_ERROR);
		}

		//删除用户(逻辑删除)
		boolean userDelete = userService.removeById(id);
		return ResultUtils.success(userDelete);
	}

	/*
	确认权限
	 */
	public boolean isAdmin(HttpServletRequest request){
		//获取用户的登陆态
		Object  userObj = request.getSession().getAttribute(USER_LOGIN_STATE);

		User user = (User) userObj;

		if (userObj == null) {
			return false; // 如果用户对象为空，直接返回false
		}


		//获取当前的权限
		if (user.getUserRole() != 1) {
			return false;
		}

		return true;
	}


}
