package com.chengfu.usercenterapi.model.domain;


import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

import lombok.Data;

/**
 * @TableName book_list
 */
@TableName(value = "book_list")
@Data
public class BookList {
	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
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

	/**
	 *逻辑删除0代表未删除1代表已删
	 */
	@TableLogic
	private Integer isDelete;

	/**
	 *
	 */
	private Date createTime;

	/**
	 *
	 */
	private Date updateTime;

	@Override
	public boolean equals(Object that) {
		if (this == that) {
			return true;
		}
		if (that == null) {
			return false;
		}
		if (getClass() != that.getClass()) {
			return false;
		}
		BookList other = (BookList) that; // 修改类型为当前类
		return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
				&& (this.getBookIsbn() == null ? other.getBookIsbn() == null : this.getBookIsbn().equals(other.getBookIsbn()))
				&& (this.getBookName() == null ? other.getBookName() == null : this.getBookName().equals(other.getBookName()))
				&& (this.getBookPrice() == null ? other.getBookPrice() == null : this.getBookPrice().equals(other.getBookPrice()))
				&& (this.getPublishDate() == null ? other.getPublishDate() == null : this.getPublishDate().equals(other.getPublishDate()))
				&& (this.getIsDelete() == null ? other.getIsDelete() == null : this.getIsDelete().equals(other.getIsDelete()))
				&& (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
				&& (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		result = prime * result + ((getBookIsbn() == null) ? 0 : getBookIsbn().hashCode());
		result = prime * result + ((getBookName() == null) ? 0 : getBookName().hashCode());
		result = prime * result + ((getBookPrice() == null) ? 0 : getBookPrice().hashCode());
		result = prime * result + ((getPublishDate() == null) ? 0 : getPublishDate().hashCode());
		result = prime * result + ((getIsDelete() == null) ? 0 : getIsDelete().hashCode());
		result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
		result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
		return result;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", id=").append(id);
		sb.append(", bookIsbn=").append(bookIsbn);
		sb.append(", bookName=").append(bookName);
		sb.append(", bookPrice=").append(bookPrice);
		sb.append(", publishDate=").append(publishDate);
		sb.append(", isDelete=").append(isDelete);
		sb.append(", createTime=").append(createTime);
		sb.append(", updateTime=").append(updateTime);
		sb.append("]");
		return sb.toString();
	}
}