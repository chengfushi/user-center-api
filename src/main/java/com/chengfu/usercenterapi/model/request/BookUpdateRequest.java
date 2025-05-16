package com.chengfu.usercenterapi.model.request;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ChengFu
 * @version 1.0
 * @date 2025/5/15 18:03
 */
@Data
public class BookUpdateRequest implements Serializable {
	private static final long serialVersionUID = 3191241716373120793L;

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

}
