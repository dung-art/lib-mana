package com.lib.manage.user.Service;

import java.util.List;

import com.lib.manage.common.Response.SuccessResponse;
import com.lib.manage.user.Dto.SystemUser.CreateSystemUserRequest;
import com.lib.manage.user.Dto.SystemUser.ListSystemUserCode;
import com.lib.manage.user.Dto.SystemUser.SystemUserDto;
import com.lib.manage.user.Dto.SystemUser.UpdateSystemUserRequest;

public interface SystemUserService {
	SuccessResponse create(CreateSystemUserRequest request);

	List<SystemUserDto> findAllSystemUser();

	SuccessResponse softDeleteSystemUser(ListSystemUserCode systemUserCodes);

	SuccessResponse updateAllField(String systemUserCode, UpdateSystemUserRequest request);
}
