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
import com.lib.manage.dto.UserInfoDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.user.CreateUserRequest;
import com.lib.manage.dto.request.user.SearchUserInfoRequest;
import com.lib.manage.dto.request.user.UpdateUserInfoRequest;
import com.lib.manage.dto.response.PageResponse;
import com.lib.manage.dto.response.SuccessResponse;
import com.lib.manage.exception.LBRException;
import com.lib.manage.service.UserService;
import com.lib.manage.util.RequestUtil;
import com.lib.manage.util.SearchUtil;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/lbm/v1/users")
public class UsersController {
    @Autowired
    private UserService userService;

	@ApiOperation(value = "API tạo người dùng")
    @PostMapping("/create")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public SuccessResponse<?> create(@Valid @RequestBody CreateUserRequest account) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException{
      return RequestUtil.ok(userService.create(account));
    }
	@ApiOperation(value = "API sửa thông tin người dùng")
    @PostMapping("/update/{id}")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public SuccessResponse<?> updateSomeFields(@PathVariable("id") String id,@Valid @RequestBody PatchRequest<UpdateUserInfoRequest> request) throws LBRException{
      return RequestUtil.ok(userService.update(id,request));
    }
	@ApiOperation(value = "API xóa người dùng")
    @PostMapping("/delete")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public SuccessResponse<?> delete(@RequestBody List<String> ids) throws LBRException{
      return RequestUtil.ok(userService.delete(ids));
    }
	@ApiOperation(value = "API xem tất cả tài khoản")
    @GetMapping("/get-all")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public PageResponse<?> findAll(@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
		      @Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
		      @RequestParam(required = false) SortOrderEnum order) {
		    Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
		    Page<UserInfoDto> pageData = userService.findAll(pageable);
		    return new PageResponse<>(pageData);
	}
	@ApiOperation(value = "API tìm người dùng theo Id")
    @GetMapping("/{id}")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public SuccessResponse<?> findById(@PathVariable String id) throws LBRException {
        return RequestUtil.ok(userService.findById(id));
    }
	@ApiOperation(value = "API tìm kiếm người dùng nâng cao")
    @GetMapping("/search")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public PageResponse<?> advanceSearch( @Valid @RequestBody SearchUserInfoRequest searchRequest,
    	      @PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
    	      @Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
    	      @RequestParam(required = false) SortOrderEnum order) {
    	    Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
    	    Page<UserInfoDto> pageData = userService.advanceSearch(searchRequest, pageable);
    	    return new PageResponse<>(pageData);
    	  }
	
	@ApiOperation(value = "API xem người dùng vi phạm")
    @GetMapping("/view/violate")
    @ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public PageResponse<?> violate(@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
    	      @Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
    	      @RequestParam(required = false) SortOrderEnum order) {
    	    Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
    	    Page<UserInfoDto> pageData = userService.violate(pageable);
    	    return new PageResponse<>(pageData);
    	  }
}
