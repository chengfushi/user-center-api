package com.chengfu.usercenterapi.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengfu.usercenterapi.common.BusinessException;
import com.chengfu.usercenterapi.common.ErrorCode;
import com.chengfu.usercenterapi.model.domain.User;
import com.chengfu.usercenterapi.service.UserService;
import com.chengfu.usercenterapi.mapper.UserMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.chengfu.usercenterapi.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 30362
* @description 针对表【user(用户信息表)】的数据库操作Service实现
* @createDate 2025-05-08 18:23:54
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

	@Resource
	private UserMapper userMapper;

	//盐值，混淆密码
	private static final String SALT = "chengfu";


	@Override
	public long userRegister(String userAccount, String userPassword, String checkPassword) {

//		校验
//		1. 账号不小于4位
//		2. 密码不小于8位(不加强校验)

		if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
		}

		if (userAccount.length() < 4 ) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数过短");
		}

		if (userPassword.length() < 8 || checkPassword.length() < 8) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数过短");
		}

//		4. 账户不包含特殊字符
		String validPattern = "^[a-zA-Z0-9]+$";

		Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
		if (!matcher.find()) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数包含敏感字符");
		}

//		5. 密码和校验密码相同
		if (!checkPassword.equals(userPassword)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"两次密码不一致");
		}

		//	3. 账户不能重复
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("login_account", userAccount);
		long count  = userMapper.selectCount(queryWrapper);

		if (count > 0) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户重复");
		}

		//对密码进行加密
		String newPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

		// 向数据库插入数据
		User user = new User();
		user.setLoginAccount(userAccount);
		user.setUserPassword(newPassword);

		boolean saveResult = this.save(user);

		if (!saveResult) {
			return -1;
		}

		return user.getId();

	}

	@Override
	public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
		//		校验
//		1. 账号不小于4位
//		2. 密码不小于8位(不加强校验)

		if (StringUtils.isAnyBlank(userAccount, userPassword)) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
		}

		if (userAccount.length() < 4 ) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数过短");
		}

		if (userPassword.length() < 8 ) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数过短");
		}

//		4. 账户不包含特殊字符
		String validPattern = "^[a-zA-Z0-9]+$";

		Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
		if (!matcher.find()) {
			throw new BusinessException(ErrorCode.PARAMS_ERROR,"账户包含敏感字符");
		}

		//对密码进行加密
		//对密码进行加密
		String encryPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

		//查询用户是否存在
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("login_account", userAccount);
		queryWrapper.eq("user_password", encryPassword);
		User user = userMapper.selectOne(queryWrapper);



		//用户不存在
		if (user == null) {
			return null;
		}

		User safetyUser = getSafetyUser(user);



		//返回脱敏后的用户信息
		//将用户放入Session
		request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
		return safetyUser;
	}

	/*
	脱敏方法
	 */
	@Override
	public User getSafetyUser(User user) {
		if (user == null) {
			return null;
		}
		//对用户信息进行脱敏
		User safetyUser = new User();
		safetyUser.setId(user.getId());
		safetyUser.setUserName(user.getUserName());
		safetyUser.setAvatarUrl(user.getAvatarUrl());
		safetyUser.setGender(user.getGender());
		safetyUser.setLoginAccount(user.getLoginAccount());
		safetyUser.setUserPassword("");
		safetyUser.setPhone(user.getPhone());
		safetyUser.setEmail(user.getEmail());
		safetyUser.setUserStatus(0);
		safetyUser.setCreateTime(user.getCreateTime());
		safetyUser.setUpdateTime(new Date());
		safetyUser.setUserRole(user.getUserRole());

		return safetyUser;
	}

	/*
	用户登出
	 */
	@Override
	public Integer userLogout(HttpServletRequest request) {
		request.getSession().removeAttribute(USER_LOGIN_STATE);

		return 1;

	}
}