package com.lib.manage.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.lib.manage.dto.UserInfoDto;
import com.lib.manage.dto.request.PatchRequest;
import com.lib.manage.dto.request.user.CreateUserRequest;
import com.lib.manage.dto.request.user.SearchUserInfoRequest;
import com.lib.manage.dto.request.user.UpdateUserInfoRequest;
import com.lib.manage.exception.LBRException;

public interface UserService {

	UserInfoDto findById(String id) throws LBRException;

	Page<UserInfoDto> findAll(Pageable pageable);

	String delete(List<String> ids) throws LBRException;

	UserInfoDto create(@Valid CreateUserRequest account);

	UserInfoDto update(String id, @Valid PatchRequest<UpdateUserInfoRequest> request) throws LBRException;

	Page<UserInfoDto> violate(Pageable pageable);

	Page<UserInfoDto> advanceSearch(@Valid SearchUserInfoRequest searchRequest, Pageable pageable);

}
