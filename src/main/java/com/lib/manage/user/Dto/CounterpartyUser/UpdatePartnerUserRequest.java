package com.lib.manage.user.Dto.CounterpartyUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePartnerUserRequest {

	private String identifyNumber;

	private String pathImageUser;

	private String roleOriginPartnerUser;

	private String groupPartnerUser;

	private String description;
}
