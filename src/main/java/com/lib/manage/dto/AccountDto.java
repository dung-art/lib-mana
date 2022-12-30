package com.lib.manage.dto;

import com.googlecode.jmapper.annotations.JGlobalMap;

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
	private String createDate;
	private String modifyDate;
	private String accountType;
    private String accountName;
	private String modifyAction;
}
