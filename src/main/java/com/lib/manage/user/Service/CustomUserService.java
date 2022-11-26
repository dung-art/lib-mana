package com.lib.manage.user.Service;

import java.util.List;

import com.lib.manage.common.Response.SuccessResponse;
import com.lib.manage.user.Dto.CustomerUser.CreateCustomUserRequest;
import com.lib.manage.user.Dto.CustomerUser.CustomUserDto;
import com.lib.manage.user.Dto.CustomerUser.ListCustomUserCode;
import com.lib.manage.user.Dto.CustomerUser.UpdateCustomUserRequest;

public interface CustomUserService {
	SuccessResponse create(CreateCustomUserRequest request);

	List<CustomUserDto> findAllCustomUser();

	SuccessResponse softDeleteCustomUser(ListCustomUserCode customUserCodes);

	SuccessResponse updateAllField(String customUserCode, UpdateCustomUserRequest request);

	List<CustomUserDto> findAllCustomUserDeleted();
}
