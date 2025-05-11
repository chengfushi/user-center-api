package com.chengfu.usercenterapi.common;


import com.chengfu.usercenterapi.utils.ResultUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public BaseResponse businessException(BusinessException e) {

		return ResultUtils.error(e.getCode(),e.getMessage(),e.getDistribution());
	}

	@ExceptionHandler(Exception.class)

	public BaseResponse runtimeException(RuntimeException e) {

		return ResultUtils.error(ErrorCode.SYSTEM_ERROR,"系统内部异常","");
	}
}
