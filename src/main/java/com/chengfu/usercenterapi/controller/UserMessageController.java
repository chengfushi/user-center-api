package com.chengfu.usercenterapi.controller;


import com.chengfu.usercenterapi.common.BaseResponse;
import com.chengfu.usercenterapi.common.BusinessException;
import com.chengfu.usercenterapi.common.ErrorCode;
import com.chengfu.usercenterapi.model.domain.User;
import com.chengfu.usercenterapi.model.domain.UserMessage;
import com.chengfu.usercenterapi.model.request.UserMessageAddRequest;
import com.chengfu.usercenterapi.service.UserMessageService;
import com.chengfu.usercenterapi.utils.ResultUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.chengfu.usercenterapi.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author ChengFu
 * @version 1.0
 * @date 2025/5/16 11:15
 */
@RestController
@RequestMapping("/message")
public class UserMessageController {

	@Autowired
	private UserMessageService userMessageService;

	/*
	 * 展示所有留言
	 * @param null
	 * @return com.chengfu.usercenterapi.common.BaseResponse<java.util.List<com.chengfu.usercenterapi.model.domain.UserMessage>>
	 * @author ChengFu
	 * @date 2025/5/16 11:31
	 */
	@PostMapping("/search")
	public BaseResponse<List<UserMessage>> search(){

		//返回所有留言
		List<UserMessage> userMessagesList = userMessageService.list();

		return ResultUtils.success(userMessagesList);

	}

		/*
	 * 添加留言
	 * @param userMessageAddRequest 留言添加请求体
	 * @param request HttpServletRequest
	 * @return com.chengfu.usercenterapi.common.BaseResponse<java.lang.Boolean>
	 * @author ChengFu
	 * @date 2025/5/16 11:31
	 */
	@PostMapping("add")
	public BaseResponse<Boolean> addMessage(@RequestBody UserMessageAddRequest userMessageAddRequest, HttpServletRequest request){

		// 获取当前用户
		Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
		User user = (User) userObj;

		if (userObj == null) {
			throw new BusinessException(ErrorCode.NO_AUTH); // 如果用户对象为空，抛出无权限异常
		}

		//创建留言对象
		UserMessage userMessage = new UserMessage();
		userMessage.setUserName(user.getUserName());
		userMessage.setMessageContent(userMessageAddRequest.getMessageContent());

		//将留言保存到数据库中
		boolean save = userMessageService.save(userMessage);
		return ResultUtils.success(save);
	}

	@DeleteMapping("/delete")
	public BaseResponse<Boolean> deleteMessage(@RequestBody Long id, HttpServletRequest request){
		//判断是否为管理员
		if (!isAdmin(request)){
			throw new BusinessException(ErrorCode.NO_AUTH);
		}

		//删除留言
		boolean b = userMessageService.removeById(id);
		return ResultUtils.success(b);
	}


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
