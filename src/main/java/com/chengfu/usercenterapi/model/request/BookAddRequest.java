package com.chengfu.usercenterapi.model.request;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ChengFu
 * @version 1.0
 * @date 2025/5/15 17:49
 */
@Data
public class BookAddRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 *
	 */
	private String bookIsbn;

	/**
	 *
	 */
	private String bookName;

	/**
	 *
	 */
	private Double bookPrice;

	/**
	 *
	 */
	private Date publishDate;

	/**
	 *存储前端传过来图片，是base64格式
	 */
	private String bookImageBase64;
}
