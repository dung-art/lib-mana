package com.lib.manage.constant;

import lombok.Getter;

public enum BookStatusEnum {
	BEING_BORROWED("BEING BORROWED"), // dang được mượn
	REPORTED_LOSS("REPORTED LOSS"), // người dùng báo mất
	IN_STOCK("IN STOCK")// trong kho , chưa được mượn
	; 

	@Getter
	private final String value;

	private BookStatusEnum(String value) {
		this.value = value;
	}
}
