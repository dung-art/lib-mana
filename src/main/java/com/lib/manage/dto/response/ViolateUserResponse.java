package com.lib.manage.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ViolateUserResponse {
	private String userCode;
	private String userName;
	private List<String> formViolation;
	private String description;
	private Float penaltyFee;
}
