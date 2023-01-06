package com.lib.manage.dto.request.account;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.lib.manage.constant.AccountTypeEnum;
import com.lib.manage.constant.Constants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccountRequest {
	
	@NotNull
	private String displayName;
	@NotNull
	private String accountName;
	@NotNull
	@Pattern(regexp = Constants.PATTERN_PASSWORD)
	private String password;
	@NotNull
	@Pattern(regexp = Constants.PATTERN_PASSWORD)
	private String confirmPassword;
	@NotNull
	private AccountTypeEnum accountType;
}
