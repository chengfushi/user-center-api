package com.chengfu.usercenterapi.model.cto;


import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.util.Date;

/**
 *封装一个返回类
 * @author Cheng Fu
 */
@Data
public class BookCTO {
	private Integer id;

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


	private Date createTime;


	private Date updateTime;

	private String bookImageBase64;
}
