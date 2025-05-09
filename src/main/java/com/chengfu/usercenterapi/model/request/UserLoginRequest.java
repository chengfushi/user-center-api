package com.chengfu.usercenterapi.model.request;


import lombok.Data;

import java.io.Serializable;

/**
 * @author Cheng Fu
 * @description: TODO
 * @date 2025/5/9 17:35
 */
@Data
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

	private String userAccount;
	private String userPassword;
}
