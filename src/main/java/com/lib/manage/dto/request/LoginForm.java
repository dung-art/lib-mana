package com.lib.manage.dto.request;

import lombok.Data;

@Data
public class LoginForm {
	private String authorization;
	private String loginCode;
}
