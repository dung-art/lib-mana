package com.lib.manage.controller;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lib.manage.constant.SortOrderEnum;
import com.lib.manage.dto.BookDto;
import com.lib.manage.dto.BookInfoDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.account.SearchAccountRequest;
import com.lib.manage.dto.request.book.AdvanceSearchBookInfoRequest;
import com.lib.manage.dto.request.book.AdvanceSearchBookRequest;
import com.lib.manage.dto.request.book.CreateBookInfoRequest;
import com.lib.manage.dto.request.book.CreateBookRequest;
import com.lib.manage.dto.request.book.UpdateBookRequest;
import com.lib.manage.dto.response.PageResponse;
import com.lib.manage.dto.response.SuccessResponse;
import com.lib.manage.exception.LBRException;
import com.lib.manage.service.BookManageService;
import com.lib.manage.service.BookService;
import com.lib.manage.util.RequestUtil;
import com.lib.manage.util.SearchUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/lbm/v1/book")
@Tag(name = "book Management Controller")
public class BookController {

	@Autowired
	private BookService bookService;
	@Autowired
	private BookManageService bookManageService;

	@ApiOperation(value = "API thêm sách")
	@PostMapping("/create")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public SuccessResponse<?> create(@RequestBody CreateBookRequest request) throws Exception {
		return RequestUtil.ok(bookManageService.create(request));
	}

	@ApiOperation(value = "API sửa sách")
	@PostMapping("/update")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public SuccessResponse<?> update(@RequestParam("id") String id,@Valid @RequestBody PatchRequest<UpdateBookRequest> request)
			throws Exception {
		return RequestUtil.ok(bookManageService.update(id,request));
	}

	@ApiOperation(value = "API xóa sách")
	@PostMapping("/delete")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public SuccessResponse<?> update(@PathVariable("id") String id) throws Exception {
		return RequestUtil.ok(bookManageService.delete(id));
	}

	@ApiOperation(value = "API tìm sách theo Id")
	@GetMapping("/{id}")
	@ResponseBody
	public SuccessResponse<?> findById(@PathVariable String id) throws LBRException {
		return RequestUtil.ok(bookManageService.findById(id));
	}

	@ApiOperation(value = "API xem tất cả sách")
	@GetMapping("/get-all")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public PageResponse<?> findAll(@RequestParam(required = false) String filter,
			@Valid @RequestBody SearchAccountRequest searchRequest,
			@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			@Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
			@RequestParam(required = false) SortOrderEnum order) {
		Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
		Page<BookDto> pageData = bookManageService.findAll(pageable);
		return new PageResponse<>(pageData);
	}

	@ApiOperation(value = "API tìm kiếm nâng cao sách")
	@GetMapping("/search")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public PageResponse<?> advanceSearch(@Valid @RequestBody AdvanceSearchBookRequest searchRequest,
			@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			@Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
			@RequestParam(required = false) SortOrderEnum order) {
		Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
		Page<BookDto> pageData = bookManageService.advanceSearch(searchRequest, pageable);
		return new PageResponse<>(pageData);
	}

	@ApiOperation(value = "API thêm thông tin sách")
	@PostMapping("/info/create")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public SuccessResponse<?> createBookInfo(@RequestBody CreateBookInfoRequest request) throws Exception {
		return RequestUtil.ok(bookService.create(request));
	}

	@ApiOperation(value = "API sửa thông tin sách")
	@PostMapping("/info/update")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public SuccessResponse<?> updateBookInfo(@RequestParam("id") String id,
			@RequestBody PatchRequest<UpdateBookRequest> request) throws Exception {
		return RequestUtil.ok(bookService.update(id, request));
	}

	@ApiOperation(value = "API xóa thông tin sách")
	@PostMapping("/info/delete")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public SuccessResponse<?> updateBookInfo(@PathVariable("id") String id) throws Exception {
		return RequestUtil.ok(bookService.delete(id));
	}

	@ApiOperation(value = "API tìm thể loại sách theo Id")
	@GetMapping("/info/{id}")
	@ResponseBody
	public SuccessResponse<?> findByIdBookInfo(@PathVariable String id) throws LBRException {
		return RequestUtil.ok(bookService.findById(id));
	}

	@ApiOperation(value = "API xem tất cả thể loại sách")
	@GetMapping("/info/get-all")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public PageResponse<?> findAllBookInfo() {
		return new PageResponse<>(null);
	}

	@ApiOperation(value = "API tìm kiếm thể loại sách , nâng cao")
	@PostMapping("/info/search")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public PageResponse<?> advanceSearchBookInfo(@Valid @RequestBody AdvanceSearchBookInfoRequest searchRequest,
			@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
			@Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
			@RequestParam(required = false) SortOrderEnum order) {
		Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
		Page<BookInfoDto> pageData = bookService.advanceSearch(searchRequest, pageable);
		return new PageResponse<>(pageData);
	}

}
