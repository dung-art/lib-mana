package com.lib.manage.controller;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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
import com.lib.manage.dto.AccountDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.account.CreateAccountRequest;
import com.lib.manage.dto.request.account.SearchAccountRequest;
import com.lib.manage.dto.request.account.UpdateAccountRequest;
import com.lib.manage.dto.response.PageResponse;
import com.lib.manage.dto.response.SuccessResponse;
import com.lib.manage.exception.LBRException;
import com.lib.manage.service.AccountService;
import com.lib.manage.util.RequestUtil;
import com.lib.manage.util.SearchUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/lbm/v1/account")
@Tag(name = "Account Management Controller")
public class AccountController {
    @Autowired
    private AccountService accountService;

	@ApiOperation(value = "API tạo tài khoản")
    @PostMapping("/create")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
    public SuccessResponse<?> create(@Valid @RequestBody CreateAccountRequest account) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException{
      return RequestUtil.ok(accountService.create(account));
    }
	@ApiOperation(value = "API sửa tài khoản")
    @PostMapping("/update/{id}")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
    public SuccessResponse<?> updateSomeFields(@PathVariable("id") String id,@Valid @RequestBody PatchRequest<UpdateAccountRequest> request) throws LBRException{
      return RequestUtil.ok(accountService.update(id,request));
    }
	@ApiOperation(value = "API xóa tài khoản")
    @PostMapping("/delete")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
    public SuccessResponse<?> deleteAccount(@RequestBody List<String> ids) throws LBRException {
      return RequestUtil.ok(accountService.deleteById(ids.get(0)));
    }
	@ApiOperation(value = "API xem tất cả tài khoản")
    @GetMapping("/get-all")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
	public PageResponse<?> findAll(@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
		      @Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
		      @RequestParam(required = false) SortOrderEnum order) {
		    Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
		    Page<AccountDto> pageData = accountService.findAllAccount(pageable);
		    return new PageResponse<>(pageData);
	}
	@ApiOperation(value = "API tìm tài khoản theo Id")
    @GetMapping("/{id}")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
    public SuccessResponse<?> findById(@PathVariable String id) throws LBRException {
        return RequestUtil.ok(accountService.findById(id));
    }
	@ApiOperation(value = "API tìm kiếm nâng cao")
    @GetMapping("/search")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN')")
    public PageResponse<?> advanceSearch(@Valid @RequestBody SearchAccountRequest searchRequest,
    	      @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
    	      @Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
    	      @RequestParam(required = false) SortOrderEnum order) {
    	    Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
    	    Page<AccountDto> pageData = accountService.advanceSearch(searchRequest, pageable);
    	    return new PageResponse<>(pageData);
    	  }


}