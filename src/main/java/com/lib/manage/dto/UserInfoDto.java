package com.lib.manage.dto;

import com.lib.manage.constant.GenderEnum;
import com.lib.manage.constant.UserStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
	private String id;
	private String createUser;
	private String createDate;
	private String modifyDate;
	private String modifiedUser;
	private String modifyAction;
	private String userCode;
	private String userName;
	private String birthDate;
	private GenderEnum gender;
	private String phoneNumber;
	private String emailAddress;
	private UserStatusEnum status;
	private String currentAddress;
	private String description;
}
