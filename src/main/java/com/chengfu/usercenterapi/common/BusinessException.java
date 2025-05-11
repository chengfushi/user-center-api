package com.chengfu.usercenterapi.common;


import lombok.Getter;

/*
全局异常处理类
@author:chengfu
 */
@Getter
public class BusinessException extends RuntimeException{
	private final int code;

	private final String distribution;

	public BusinessException(int code, String message,String distribution) {
		super(message);
		this.code = code;
		this.distribution = distribution;
	}

	public BusinessException(ErrorCode errorCode) {
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
		this.distribution = errorCode.getDistribution();
	}

	public BusinessException(ErrorCode errorCode,String distribution) {
		super(errorCode.getMessage());
		this.code = errorCode.getCode();
		this.distribution = distribution;
	}

}
