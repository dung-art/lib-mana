package com.lib.manage.user.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jmapper.JMapper;
import com.lib.manage.common.Checks.Checks;
import com.lib.manage.common.Convert.Convert;
import com.lib.manage.common.Response.FailResponse;
import com.lib.manage.common.Response.ResponseObject;
import com.lib.manage.common.Response.SuccessMessageResponse;
import com.lib.manage.common.Response.SuccessResponse;
import com.lib.manage.user.Dto.CreateUserResponse;
import com.lib.manage.user.Dto.SystemUser.CreateSystemUserRequest;
import com.lib.manage.user.Dto.SystemUser.ListSystemUserCode;
import com.lib.manage.user.Dto.SystemUser.SystemUserDto;
import com.lib.manage.user.Dto.SystemUser.UpdateSystemUserRequest;
import com.lib.manage.user.Entity.SystemUser;
import com.lib.manage.user.Map.MapUser;
import com.lib.manage.user.Repo.SystemUserRepo;

@Service
@Transactional
public class SystemUserServiceImpl implements SystemUserService {
	@Autowired
	SystemUserRepo systemUserRepo;

	private JMapper<SystemUserDto, SystemUser> mapper = new JMapper<>(SystemUserDto.class, SystemUser.class);
	private JMapper<SystemUser, CreateSystemUserRequest> cmapper = new JMapper<>(SystemUser.class,
			CreateSystemUserRequest.class);

	@Override
	public SuccessResponse create(CreateSystemUserRequest request) {
		try {
			Optional<SystemUser> oSystemUser = systemUserRepo.findById(request.getSystemUserCode());
			if (oSystemUser.isPresent()) {
				return new FailResponse("Thông tin về người dùng này đã tồn tại !");
			}
			if (!request.getIdentifyNumber().equals(null)
					&& systemUserRepo.findByIdentifyNumber(request.getIdentifyNumber()).isPresent()) {
				return new FailResponse("Số định danh cá nhân không được trùng lặp !");
			}
			if (!Checks.checkStringIsFormatDate(request.getBirthDate())) {
				return new FailResponse("Ngày không đúng định dạng ! (Vui lòng nhập theo định dạng : dd/MM/yyyy)");
			} else {
				if (Checks.isSystemUserCode(request.getSystemUserCode())) {
					SystemUser systemUser = cmapper.getDestination(request);
					systemUser.setRoleOriginSystemUser(Convert.getRoleOriginSystemUser(request.getSystemUserCode()));
					systemUserRepo.save(systemUser);
					return new CreateUserResponse(systemUser.getSystemUserCode(),
							"Thêm mới tài khoản người dùng thành công !");
				}
				return new FailResponse("Vui lòng nhập đúng định dạng mã người dùng hệ thống: SYS + xx + yyy !");
			}
		} catch (Exception e) {
			return new FailResponse("Đã có lỗi xãy ra !");
		}
	}

	@Override
	public SuccessResponse softDeleteSystemUser(ListSystemUserCode systemUserCodes) {
		List<SystemUser> systemUsers = systemUserRepo.findAllById(systemUserCodes.getSystemUserCodes());
		if (systemUsers.isEmpty() || systemUsers == null) {
			return new FailResponse(
					"Không tìm thấy tài khoản người dùng nào được chỉ định hoặc không có tài khoản người dùng nào được xóa !");
		}
		for (SystemUser systemUser : systemUsers) {
			systemUser.setIsDelete(true);
		}
		return new SuccessMessageResponse("Xóa thành công !");
	}

	@Override
	public List<SystemUserDto> findAllSystemUser() {
		List<SystemUser> systemUsers = systemUserRepo.findAllNoDelete();
		if (systemUsers.isEmpty() || systemUsers == null) {
			return null;
		}
		List<SystemUserDto> systemUserDtos = new ArrayList<>();
		for (SystemUser systemUser : systemUsers) {
			systemUserDtos.add(mapper.getDestination(systemUser));
		}
		return systemUserDtos;
	}

	@Override
	public SuccessResponse updateAllField(String systemUserCode, UpdateSystemUserRequest request) {
		try {
			Optional<SystemUser> oSystemUser = systemUserRepo.findById(systemUserCode);
			if (!oSystemUser.isPresent()) {
				return new FailResponse("Tài khoản người dùng chưa tồn tại!");
			}
			if (oSystemUser.get().getIsDelete()) {
				return new FailResponse("Tài khoản người dùng đã bị xóa !");
			} else {
				if (oSystemUser.get().getIdentifyNumber().equals(request.getIdentifyNumber())
						&& oSystemUser.get().getPathImage().equals(request.getPathImage())
						&& oSystemUser.get().getRoleOriginSystemUser().equals(request.getRoleOriginSystemUser())
						&& oSystemUser.get().getGroupUser().equals(request.getGroupUser())
						&& oSystemUser.get().getDescription().equals(request.getDescription())) {
					return new FailResponse("Tài khoản người dùng chưa được sửa !");
				}
				SystemUser systemUser = oSystemUser.get();
				systemUser = MapUser.getSystemUserFromUpdateUSer(request, systemUser);
				systemUserRepo.save(systemUser);
				SystemUserDto dto = mapper.getDestination(systemUser);
				dto.setModifiedDate(LocalDateTime.now());
				return new ResponseObject<SystemUserDto>(dto);
			}
		} catch (Exception e) {
			return new FailResponse("Cập nhật tài khoản người dùng thất bại !");
		}

	}

}
