package com.lib.manage.controller;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lib.manage.constant.ErrorEnum;
import com.lib.manage.dto.request.account.UpdatePasswordRequest;
import com.lib.manage.dto.response.SuccessResponse;
import com.lib.manage.dto.response.account.CurrentAccountResponse;
import com.lib.manage.exception.LBRException;
import com.lib.manage.models.account.CustomAccountDetails;
import com.lib.manage.service.AccountService;
import com.lib.manage.util.RequestUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javassist.NotFoundException;

@RestController
@RequestMapping("/lbm/v1/account")
@Tag(name = "Account Management Controller")
public class AuthController {
	@Autowired
	private AccountService accountService;

	@ApiOperation(value = "API đăng nhập")
	@PostMapping("/login")
	@ResponseBody
	@PermitAll
	public SuccessResponse<?> login(@RequestHeader String Authorization, @RequestHeader String accountName)
			throws Exception {
		return RequestUtil.ok(accountService.generateJwtToken(Authorization, accountName));
	}
	
    @ApiOperation(value = "API đổi mật khẩu")
	@PostMapping("/changePassword")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public SuccessResponse<?> changePassword(@Valid @RequestBody UpdatePasswordRequest changePasswordForm,
			BindingResult bindingResult) throws NotFoundException, LBRException {
		if (bindingResult.hasFieldErrors()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, "not found");
		}
		return RequestUtil.ok(accountService.updatePassword(changePasswordForm));
	}
    
    @ApiOperation(value = "API xem thông tin tài khoản hiện tại")
	@GetMapping("/me")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public SuccessResponse<?> currentUser() {
		CustomAccountDetails authUser = (CustomAccountDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		CurrentAccountResponse ret = new CurrentAccountResponse();
		ret.setId(authUser.getId());
		ret.setAccountName(authUser.getUsername());
		ret.setDisplayName(authUser.getDisplayName());
		return RequestUtil.ok(ret);
	}
    
    @ApiOperation(value = "API đăng xuất")
	@PostMapping("/logout")
	@ResponseBody
	@PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
	public SuccessResponse<?> logout(@RequestHeader String token)
			throws Exception {
		return RequestUtil.ok(accountService.revokeToken(token));
	}
}