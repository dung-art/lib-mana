//package com.lib.manage.service;
//
//import java.util.List;
//
//import com.lib.manage.dto.ListSystemUserCode;
//import com.lib.manage.dto.SystemUserDto;
//import com.lib.manage.dto.request.CreateSystemUserRequest;
//import com.lib.manage.dto.request.UpdateSystemUserRequest;
//import com.lib.manage.dto.response.SuccessResponse;
//
//public interface SystemUserService {
//	SuccessResponse create(CreateSystemUserRequest request);
//
//	List<SystemUserDto> findAllSystemUser();
//
//	SuccessResponse softDeleteSystemUser(ListSystemUserCode systemUserCodes);
//
//	SuccessResponse updateAllField(String systemUserCode, UpdateSystemUserRequest request);
//}
