package com.chengfu.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chengfu.usercenter.model.domain.User;
import com.chengfu.usercenter.service.UserService;
import com.chengfu.usercenter.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.net.openssl.ciphers.MessageDigest;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	@Override
	public long userRegister(String userAccount, String userPassword, String checkPassword) {

//		校验
//		1. 账号不小于4位
//		2. 密码不小于8位(不加强校验)

		if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
			return -1;
		}

		if (userAccount.length() < 4 ) {
			return -1;
		}

		if (userPassword.length() < 8 || checkPassword.length() < 8) {
			return -1;
		}

//		4. 账户不包含特殊字符
		String validPattern = "^[a-zA-Z0-9]+$";

		Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
		if (!matcher.find()) {
			return  - 1;
		}

//		5. 密码和校验密码相同
		if (!checkPassword.equals(userPassword)) {
			return -1;
		}

		//	3. 账户不能重复
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("login_account", userAccount);
		long count  = userMapper.selectCount(queryWrapper);

		if (count > 0) {
			return -1;
		}

		//对密码进行加密
		String newPassword = DigestUtils.md5DigestAsHex(userPassword.getBytes());

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
}




