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
import com.lib.manage.common.Response.FailResponse;
import com.lib.manage.common.Response.ResponseObject;
import com.lib.manage.common.Response.SuccessMessageResponse;
import com.lib.manage.common.Response.SuccessResponse;
import com.lib.manage.user.Dto.CreateUserResponse;
import com.lib.manage.user.Dto.CounterpartyUser.CreatePartnerUserRequest;
import com.lib.manage.user.Dto.CounterpartyUser.ListPartnerUserCode;
import com.lib.manage.user.Dto.CounterpartyUser.PartnerUserDto;
import com.lib.manage.user.Dto.CounterpartyUser.UpdatePartnerUserRequest;
import com.lib.manage.user.Entity.PartnerUser;
import com.lib.manage.user.Map.MapUser;
import com.lib.manage.user.Repo.PartnerUserRepo;

@Service
@Transactional
public class PartnerUserServiceImpl implements PartnerUserService {
	@Autowired
	PartnerUserRepo partnerUserRepo;

	private JMapper<PartnerUserDto, PartnerUser> mapper = new JMapper<>(PartnerUserDto.class, PartnerUser.class);
	private JMapper<PartnerUser, CreatePartnerUserRequest> cmapper = new JMapper<>(PartnerUser.class,
			CreatePartnerUserRequest.class);

	@Override
	public SuccessResponse create(CreatePartnerUserRequest request) {
		try {
			Optional<PartnerUser> oSystemUser = partnerUserRepo.findById(request.getPartnerUserCode());
			if (oSystemUser.isPresent()) {
				return new FailResponse("Thông tin về người dùng này đã tồn tại !");
			}
			if (!request.getIdentifyNumber().equals(null)
					&& partnerUserRepo.findByIdentifyNumber(request.getIdentifyNumber()).isPresent()) {
				return new FailResponse("Số định danh cá nhân không được trùng lặp !");
			}
			if (!Checks.checkStringIsFormatDate(request.getBirthDate())) {
				return new FailResponse("Ngày không đúng định dạng ! (Vui lòng nhập theo định dạng : dd/MM/yyyy)");
			} else {
				if (Checks.isPartnerUserCode(request.getPartnerUserCode())) {
					PartnerUser systemUser = cmapper.getDestination(request);
					partnerUserRepo.save(systemUser);
					return new CreateUserResponse(systemUser.getPartnerUserCode(),
							"Thêm mới tài khoản người dùng thành công !");
				}
				return new FailResponse("Vui lòng nhập đúng định dạng mã người dùng đối tác: CUST + xxxx + yyyyy !");
			}
		} catch (Exception e) {
		}
		return new FailResponse("Đã có lỗi xãy ra !");
	}

	@Override
	public List<PartnerUserDto> findAllPartnerUser() {
		List<PartnerUser> partnerUsers = partnerUserRepo.findAllNoDelete();
		if (partnerUsers.isEmpty() || partnerUsers == null) {
			return null;
		}
		List<PartnerUserDto> partnerUserDtos = new ArrayList<>();
		for (PartnerUser partnerUser : partnerUsers) {
			partnerUserDtos.add(mapper.getDestination(partnerUser));
		}
		return partnerUserDtos;
	}

	@Override
	public SuccessResponse softDeletePartnerUser(ListPartnerUserCode partnerUserCodes) {
		List<PartnerUser> partnerUsers = partnerUserRepo.findAllById(partnerUserCodes.getPartnerUserCodes());
		if (partnerUsers.isEmpty() || partnerUsers == null) {
			return new FailResponse(
					"Không tìm thấy tài khoản người dùng nào được chỉ định hoặc không có tài khoản người dùng nào được xóa !");
		}
		for (PartnerUser partnerUser : partnerUsers) {
			partnerUser.setIsDelete(true);
		}
		return new SuccessMessageResponse("Xóa thành công !");
	}

	@Override
	public SuccessResponse updateAllField(String partnerUserCode, UpdatePartnerUserRequest request) {
		try {
			Optional<PartnerUser> oPartnerUser = partnerUserRepo.findById(partnerUserCode);
			if (!oPartnerUser.isPresent()) {
				return new FailResponse("Tài khoản người dùng chưa tồn tại!");
			}
			if (oPartnerUser.get().getIsDelete()) {
				return new FailResponse("Tài khoản người dùng đã bị xóa !");
			} else {
				if (oPartnerUser.get().getIdentifyNumber().equals(request.getIdentifyNumber())
						&& oPartnerUser.get().getPathImageUser().equals(request.getPathImageUser())
						&& oPartnerUser.get().getRoleOriginPartnerUser().equals(request.getRoleOriginPartnerUser())
						&& oPartnerUser.get().getGroupPartnerUser().equals(request.getGroupPartnerUser())
						&& oPartnerUser.get().getDescription().equals(request.getDescription())) {
					return new FailResponse("Tài khoản người dùng chưa được sửa !");
				}
				PartnerUser partnerUser = oPartnerUser.get();
				partnerUser = MapUser.getPartnerUserFromUpdateUSer(request, partnerUser);
				partnerUserRepo.save(partnerUser);
				PartnerUserDto dto = mapper.getDestination(partnerUser);
				dto.setModifiedDate(LocalDateTime.now());
				return new ResponseObject<PartnerUserDto>(dto);
			}
		} catch (Exception e) {
		}
		return new FailResponse("Cập nhật tài khoản người dùng thất bại !");

	}
}
