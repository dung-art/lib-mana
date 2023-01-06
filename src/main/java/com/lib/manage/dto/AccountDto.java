package com.lib.manage.dto;

import java.time.LocalDateTime;

import com.googlecode.jmapper.annotations.JGlobalMap;
import com.lib.manage.constant.AccountTypeEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JGlobalMap
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
	private String id;
	private Boolean isDeleted = false;
	private String createUser;
	private LocalDateTime createDate;
	private LocalDateTime modifyDate;
	private String modifiedUser;
	private String modifyAction;
	private String displayName;
	private AccountTypeEnum accountType;
	private String accountName;
}
