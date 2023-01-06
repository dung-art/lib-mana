package com.lib.manage.constant;

import lombok.Getter;

public enum BorrowStatusEnum {
	BORROWING("BORROWING"), // đang mượn
	REPORTED("REPORTED"), // đã trả
	LOSS("LOSS") // bị mất sách
	; 

	@Getter
	private final String value;

	private BorrowStatusEnum(String value) {
		this.value = value;
	}
}
