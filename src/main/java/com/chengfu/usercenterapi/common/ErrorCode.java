package com.chengfu.usercenterapi.common;

import lombok.Getter;

/**
 * @author Cheng Fu
 * @description: 错误代码枚举类
 * @date 2025/5/11 21:28
 */
@Getter
public enum ErrorCode {

	//成功登录
	SUCCESS(0,"成功",""),

	//请求参数错误
	PARAMS_ERROR(40000,"请求参数错误",""),

	//请求数据为空
	PARAMS_NULL_ERROR(40001,"请求数据为空",""),

	//没有权限
	NO_AUTH(40100,"无权限",""),

	//未登录
	NOT_LOGIN(40101,"未登录",""),

	//系统内部异常
	SYSTEM_ERROR(50000,"系统内部异常","")

	;


	//错误码
	private final int code;

	//错误信息
	private final String message;

	//信息详情
	private final String distribution;

	ErrorCode(int code, String message, String distribution) {
		this.code = code;
		this.message = message;
		this.distribution = distribution;
	}
}
