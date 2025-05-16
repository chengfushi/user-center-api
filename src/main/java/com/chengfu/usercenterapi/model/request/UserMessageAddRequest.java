package com.chengfu.usercenterapi.model.request;


import lombok.Data;

/**
 * @author ChengFu
 * @version 1.0
 * @date 2025/5/16 15:27
 */
@Data
public class UserMessageAddRequest {
	private static final long serialVersionUID = 1L;

	/**
	 * 留言内容
	 */
	private String messageContent;
}
