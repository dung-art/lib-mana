package com.lib.manage.dto.request.user;

import java.util.List;

import com.lib.manage.constant.GenderEnum;
import com.lib.manage.constant.UserStatusEnum;
import com.lib.manage.dto.DateRange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchUserInfoRequest {
	private List<String> createUser;
	private DateRange createDate;
	private DateRange modifyDate;
	private List<String> modifiedUser;
	private String userCode;
	private String userName;
	private DateRange birthDate;
	private List<GenderEnum> gender;
	private String phoneNumber;
	private String emailAddress;
	private List<UserStatusEnum> status;
	private String currentAddress;
}
