package com.lib.manage.service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lib.manage.dto.AccountDto;
import com.lib.manage.dto.ChangePasswordForm;
import com.lib.manage.dto.response.JwtResponse;
import com.lib.manage.entity.Account;
import com.lib.manage.exception.LBRException;

import javassist.NotFoundException;


public interface AccountService extends UserDetailsService {

	Account findByAccountName(String accountName) throws NotFoundException;

	JwtResponse generateJwtToken(String Authorization, String accountName)
			throws UsernameNotFoundException, LBRException;

	AccountDto changePassword(ChangePasswordForm changePasswordForm) throws NotFoundException, LBRException;

}