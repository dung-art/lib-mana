package com.lib.manage.user.Service;

import java.util.List;

import com.lib.manage.common.Response.SuccessResponse;
import com.lib.manage.user.Dto.CounterpartyUser.CreatePartnerUserRequest;
import com.lib.manage.user.Dto.CounterpartyUser.ListPartnerUserCode;
import com.lib.manage.user.Dto.CounterpartyUser.PartnerUserDto;
import com.lib.manage.user.Dto.CounterpartyUser.UpdatePartnerUserRequest;

public interface PartnerUserService {
	SuccessResponse create(CreatePartnerUserRequest request);

	List<PartnerUserDto> findAllPartnerUser();

	SuccessResponse softDeletePartnerUser(ListPartnerUserCode partnerUserCodes);

	SuccessResponse updateAllField(String systemUserCode, UpdatePartnerUserRequest request);

}
