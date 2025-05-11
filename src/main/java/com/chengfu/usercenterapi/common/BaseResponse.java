package com.chengfu.usercenterapi.common;


import lombok.Data;

import java.io.Serializable;

/**
 * @author Cheng Fu
 * @description: 通用返回对象
 * @date 2025/5/11 19:43
 */
@Data
public class BaseResponse<T> implements Serializable {
	private int code;

	private T data;

	private String message;

	private String distribution;

	public BaseResponse(int code, T data, String message,String distribution) {
		this.code = code;
		this.data = data;
		this.message = message;
		this.distribution = distribution;
	}

	public BaseResponse(int code, T data,String message) {
		this(code, data, "","");
	}

	public BaseResponse(int code,String message,String distribution) {
		this(code, null, message,distribution);
	}

	public BaseResponse(int code, T data) {
		this(code, data,"");
	}

	public BaseResponse(ErrorCode errorCode) {
		this(errorCode.getCode(),null,errorCode.getMessage(),errorCode.getDistribution());
	}

	public BaseResponse(ErrorCode errorCode,String message) {
		this(errorCode.getCode(),null,message,errorCode.getDistribution());
	}

	public BaseResponse(ErrorCode errorCode,String message,String distribution) {
		this(errorCode.getCode(),null,message,distribution);
	}
}
