package com.lib.manage.user.Dto.SystemUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSystemUserRequest {

	private String identifyNumber;

	private String pathImage;

	private String roleOriginSystemUser;

	private String groupUser;

	private String description;
}
