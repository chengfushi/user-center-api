package com.chengfu.usercenterapi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chengfu.usercenterapi.common.BaseResponse;
import com.chengfu.usercenterapi.common.BusinessException;
import com.chengfu.usercenterapi.common.ErrorCode;
import com.chengfu.usercenterapi.model.domain.BookList;
import com.chengfu.usercenterapi.model.domain.User;
import com.chengfu.usercenterapi.model.request.BookAddRequest;
import com.chengfu.usercenterapi.model.request.BookUpdateRequest;
import com.chengfu.usercenterapi.service.BookListService;
import com.chengfu.usercenterapi.utils.ResultUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.chengfu.usercenterapi.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/book")

public class BookListController {
	@Autowired
	private BookListService bookListService;

	@PostMapping("/add")
	/*
	 *将新书添加到数据库中
	 * @param BookAddRequest 书籍请求
	 * @return com.chengfu.usercenterapi.common.BaseResponse
	 * @author ChengFu
	 * @date 2025/5/15 17:55
	 */
	public BaseResponse<Boolean> addBook(@RequestBody BookAddRequest bookAddRequest, HttpServletRequest request) {

		//非管理员无法增加数据
		if (!isAdmin(request)){
			throw new BusinessException(ErrorCode.NO_AUTH);
		}
		//将数据保存到数据库中
		BookList bookList = new BookList();
		bookList.setBookIsbn(bookAddRequest.getBookIsbn());
		bookList.setBookName(bookAddRequest.getBookName());
		bookList.setBookPrice(bookAddRequest.getBookPrice());
		bookList.setPublishDate(bookAddRequest.getPublishDate());

		//将数据保存到数据库中
		boolean save = bookListService.save(bookList);

		return ResultUtils.success(save);
	}


	@GetMapping("/search")
	/*
	 * 通过书名查找数据
	 * @param bookSearchRequest 图书搜索请求
	 * @return com.chengfu.usercenterapi.common.BaseResponse
	 * @author ChengFu
	 * @date 2025/5/15 17:56
	 */
	public BaseResponse<List<BookList>> searchBook(String bookName){

		//根据书名查找数据
		QueryWrapper<BookList> queryWrapper = new QueryWrapper<>();
		queryWrapper.like("book_name", bookName);
		List<BookList> bookLists = bookListService.list(queryWrapper);

		return ResultUtils.success(bookLists);

	}

	@PatchMapping("/update")
	public BaseResponse updateBook(@RequestBody BookUpdateRequest bookUpdateRequest, HttpServletRequest httpServletRequest){
		//非管理员无法进行数据更新
		if (!isAdmin(httpServletRequest)){
			throw new BusinessException(ErrorCode.NO_AUTH);
		}
		//替换相同isbn的数据
		QueryWrapper<BookList> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("book_isbn", bookUpdateRequest.getBookIsbn());

		BookList bookList = new BookList();
		bookList.setBookIsbn(bookUpdateRequest.getBookIsbn());
		bookList.setBookName(bookUpdateRequest.getBookName());
		bookList.setBookPrice(bookUpdateRequest.getBookPrice());
		bookList.setPublishDate(bookUpdateRequest.getPublishDate());

		boolean update = bookListService.update(bookList, queryWrapper);
		return ResultUtils.success(update);

	}

	@DeleteMapping("/delete")
	/*
	 * 删除图书信息
	 * @param bookIsbn 图书ISBN号（JSON格式：{"bookIsbn":"xxx"}）
	 * @param request HTTP请求
	 * @return com.chengfu.usercenterapi.common.BaseResponse
	 * @author ChengFu
	 */
	public BaseResponse deleteBook(@RequestBody String bookIsbn, HttpServletRequest request){
		if (!isAdmin(request)){
			throw new BusinessException(ErrorCode.NO_AUTH);
		}
		// 校验参数
		if (bookIsbn.isEmpty()) {
			throw new BusinessException(ErrorCode.PARAMS_NULL_ERROR, "ISBN不能为空");
		}
		
		QueryWrapper<BookList> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("book_isbn", bookIsbn);

		boolean delete = bookListService.remove(queryWrapper);

		return ResultUtils.success(delete);
	}



	public boolean isAdmin(HttpServletRequest request){
		//获取用户的登陆态
		Object  userObj = request.getSession().getAttribute(USER_LOGIN_STATE);

		User user = (User) userObj;

		if (userObj == null) {
			return false; // 如果用户对象为空，直接返回false
		}


		//获取当前的权限
		if (user.getUserRole() != 1) {
			return false;
		}

		return true;
	}

}
