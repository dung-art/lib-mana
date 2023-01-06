package com.lib.manage.constant;

import lombok.Getter;

public enum GenderEnum {
	MALE("MALE"),
	FEMALE("FEMALE"),
	OTHER("OTHER")
	; 

	@Getter
	private final String value;

	private GenderEnum(String value) {
		this.value = value;
	}
}
