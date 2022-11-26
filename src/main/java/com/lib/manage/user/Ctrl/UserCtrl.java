package com.lib.manage.user.Ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lib.manage.common.Response.FailResponse;
import com.lib.manage.common.Response.ResponseObject;
import com.lib.manage.common.Response.SuccessResponse;
import com.lib.manage.user.Dto.CounterpartyUser.CreatePartnerUserRequest;
import com.lib.manage.user.Dto.CounterpartyUser.ListPartnerUserCode;
import com.lib.manage.user.Dto.CounterpartyUser.PartnerUserDto;
import com.lib.manage.user.Dto.CounterpartyUser.UpdatePartnerUserRequest;
import com.lib.manage.user.Dto.CustomerUser.CreateCustomUserRequest;
import com.lib.manage.user.Dto.CustomerUser.CustomUserDto;
import com.lib.manage.user.Dto.CustomerUser.ListCustomUserCode;
import com.lib.manage.user.Dto.CustomerUser.UpdateCustomUserRequest;
import com.lib.manage.user.Dto.SystemUser.CreateSystemUserRequest;
import com.lib.manage.user.Dto.SystemUser.ListSystemUserCode;
import com.lib.manage.user.Dto.SystemUser.SystemUserDto;
import com.lib.manage.user.Dto.SystemUser.UpdateSystemUserRequest;
import com.lib.manage.user.Service.CustomUserService;
import com.lib.manage.user.Service.PartnerUserService;
import com.lib.manage.user.Service.SystemUserService;

@RestController
@RequestMapping("/api/v1/users")
@Validated
@Transactional
public class UserCtrl {

	@Autowired
	private SystemUserService systemUserService;

	@Autowired
	private PartnerUserService partnerUserService;

	@Autowired
	private CustomUserService customUserService;

// SystemUser
	@PostMapping("/system-user")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse create(@RequestBody CreateSystemUserRequest request) {
		return systemUserService.create(request);
	}

	@GetMapping("/system-user/all-system-user")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse findAllSystemUser() {
		List<SystemUserDto> dtos = systemUserService.findAllSystemUser();
		if (dtos == null || dtos.isEmpty()) {
			return new FailResponse(
					"Không tìm thấy tài khoản người dùng nào hoặc các tài khoản người dùng đã được xóa !");
		}
		return new ResponseObject<>(dtos);
	}

	@PostMapping("/system-user/delete-system-user")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse softDeleteSystemUser(@RequestBody ListSystemUserCode systemUserCodes) {
		return systemUserService.softDeleteSystemUser(systemUserCodes);
	}

	@PostMapping("/system-user/{id}/update-system-user")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SuccessResponse updateAllFieldSystemUser(@PathVariable("id") String systemUserCode,
			@RequestBody UpdateSystemUserRequest request) throws Exception {
		return systemUserService.updateAllField(systemUserCode, request);
	}

//	@PostMapping("/system-user/add-role/system-user")
//	@ResponseStatus(HttpStatus.OK)
//	public SuccessResponse addRoleForSystemUser(@RequestBody CreateSystemUserRequest request) {
//		return systemUserService.create(request);
//	}
//	
//	@PostMapping("/system-user/add-group/system-user")
//	@ResponseStatus(HttpStatus.OK)
//	public SuccessResponse addSystemUserInGroup(@RequestBody CreateSystemUserRequest request) {
//		return systemUserService.create(request);
//	}

//PartnerUser
	@PostMapping("/partner-user")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse create(@RequestBody CreatePartnerUserRequest request) {
		return partnerUserService.create(request);
	}

	@GetMapping("/partner-user/all-partner-user")
	@ResponseBody
	public SuccessResponse findAllPartnerUser() {
		List<PartnerUserDto> dtos = partnerUserService.findAllPartnerUser();
		if (dtos == null || dtos.isEmpty()) {
			return new FailResponse(
					"Không tìm thấy tài khoản người dùng nào hoặc các tài khoản người dùng đã được xóa !");
		}
		return new ResponseObject<>(dtos);
	}

	@PostMapping("/partner-user/delete-partner-user")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse softDeletePartnerUser(@RequestBody ListPartnerUserCode partnerUserCodes) {
		return partnerUserService.softDeletePartnerUser(partnerUserCodes);
	}

	@PostMapping("/partner-user/{id}/update-partner-user")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SuccessResponse updateAllFieldPartnerUser(@PathVariable("id") String partnerUserCode,
			@RequestBody UpdatePartnerUserRequest request) throws Exception {
		return partnerUserService.updateAllField(partnerUserCode, request);
	}

	// CustomerUser
	@PostMapping("/custom-user")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse create(@RequestBody CreateCustomUserRequest request) {
		return customUserService.create(request);
	}

	@GetMapping("/customer-user/all-custom-user")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse findAllCustomUser() {
		List<CustomUserDto> dtos = customUserService.findAllCustomUser();
		if (dtos == null || dtos.isEmpty()) {
			return new FailResponse(
					"Không tìm thấy tài khoản người dùng nào hoặc các tài khoản người dùng đã được xóa !");
		}
		return new ResponseObject<>(dtos);
	}

	@PostMapping("/customer-user/delete-custom-user")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse softDeleteCustomUser(@RequestBody ListCustomUserCode customUserCodes) {
		return customUserService.softDeleteCustomUser(customUserCodes);
	}

	@PostMapping("/customer-user/{id}/update-custom-user")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SuccessResponse updateAllFieldCustomUser(@PathVariable("id") String systemUserCode,
			@RequestBody UpdateCustomUserRequest request) throws Exception {
		return customUserService.updateAllField(systemUserCode, request);
	}
}