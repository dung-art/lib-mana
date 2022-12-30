package com.lib.manage.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lib.manage.constant.ErrorEnum;
import com.lib.manage.dto.ChangePasswordForm;
import com.lib.manage.dto.response.SuccessResponse;
import com.lib.manage.exception.LBRException;
import com.lib.manage.service.AccountService;
import com.lib.manage.util.RequestUtil;

import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
@CrossOrigin("*")
@RequestMapping("/lbm/v1/account")
public class AuthController {
	@Autowired
	private AccountService accountService;

    @ApiOperation(value = "API đăng nhập")
	@PostMapping("/login")
    @ResponseBody
	public SuccessResponse<?> login(@RequestHeader String Authorization ,@RequestHeader String accountName) throws UsernameNotFoundException, LBRException {
		return RequestUtil.ok(accountService.generateJwtToken(Authorization,accountName));
	}

	@PutMapping("/changePassword")
	public SuccessResponse<?> changePassword(@Valid @RequestBody ChangePasswordForm changePasswordForm,
			BindingResult bindingResult) throws NotFoundException, LBRException {
		if (bindingResult.hasFieldErrors()) {
			throw new LBRException(ErrorEnum.NOT_FOUND, "not found");
		}
		return RequestUtil.ok(accountService.changePassword(changePasswordForm));
	}
}