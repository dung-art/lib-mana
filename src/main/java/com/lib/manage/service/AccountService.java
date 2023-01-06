package com.lib.manage.service;
import java.lang.reflect.InvocationTargetException;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.lib.manage.dto.AccountDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.account.CreateAccountRequest;
import com.lib.manage.dto.request.account.ResetPasswordRequest;
import com.lib.manage.dto.request.account.SearchAccountRequest;
import com.lib.manage.dto.request.account.UpdateAccountRequest;
import com.lib.manage.dto.request.account.UpdatePasswordRequest;
import com.lib.manage.dto.response.JwtResponse;
import com.lib.manage.entity.Account;
import com.lib.manage.exception.LBRException;

import javassist.NotFoundException;


public interface AccountService extends UserDetailsService {

	JwtResponse generateJwtToken(String Authorization, String accountName)
			throws UsernameNotFoundException, LBRException, Exception;

	AccountDto findAccountByAccountName(String accountName) throws LBRException;

	Account findByAccountName(String accountName) throws NotFoundException;

	Account create(@Valid CreateAccountRequest accountRequest) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;

	String deleteById(String id) throws LBRException;

	void setActive(String id, boolean isActive) throws LBRException;

	String updatePassword(@Valid UpdatePasswordRequest request) throws NotFoundException;

	void resetPassword(@Valid ResetPasswordRequest request) throws NotFoundException;

	String revokeToken(String token) throws NotFoundException;

	Page<AccountDto> findAllAccount(Pageable pageable);

	Account findById(String id) throws LBRException;

	Object update(String id, @Valid PatchRequest<UpdateAccountRequest> request) throws LBRException;

	Page<AccountDto> advanceSearch(@Valid SearchAccountRequest searchRequest, Pageable pageable);


}