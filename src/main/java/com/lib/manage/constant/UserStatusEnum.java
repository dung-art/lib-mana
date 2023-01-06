package com.lib.manage.constant;

import lombok.Getter;

public enum UserStatusEnum {
	FORBIDDEN("FORBIDDEN"),  // bị cấm
	DO_NOT_BORROW("DO_NOT_BORROW"), // ko mượn/ chưa mượn
	BORROWING("BORROWING"), // đang mượn
	VIOLATE("VIOLATE") // vi phạm
;

	@Getter
	private final String value;

	private UserStatusEnum(String value) {
		this.value = value;
	}
}
