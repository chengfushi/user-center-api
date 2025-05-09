package com.chengfu.usercenterapi.model.request;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Cheng Fu
 * @description: 这是用户的DTO
 * @date 2025/5/9 17:21
 */
@Data
public class UserRegisterRequest implements Serializable {
	private static final long serialVersionUID = 3191241716373120793L;

	private String userAccount;
	private String userPassword;
	private String checkPassword;



}
