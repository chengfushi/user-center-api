package com.chengfu.usercenterapi.utils;


import com.chengfu.usercenterapi.common.BaseResponse;
import com.chengfu.usercenterapi.common.ErrorCode;

/**
 * @author Cheng Fu
 * @description: 工具类去生成返回对象
 * @date 2025/5/11 19:50
 */
public class ResultUtils {

	/*
	成功
	 */
	public static <T>BaseResponse<T> success(T data){
		return new BaseResponse<>(0,data,"success");
	}

	/*
	失败
	 */
	public static<T> BaseResponse<T> error(ErrorCode errorCode) {
		return new BaseResponse<>(errorCode);
	}
	public static<T> BaseResponse<T> error(ErrorCode errorCode,String message) {
		return new BaseResponse<>(errorCode,message);
	}

	public static<T> BaseResponse<T> error(ErrorCode errorCode,String message,String distribution) {
		return new BaseResponse<>(errorCode,message,distribution);
	}

	public static<T> BaseResponse<T> error(int code,String message,String distribution) {
		return new BaseResponse<>(code,message,distribution);
	}
}
