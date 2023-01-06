package com.lib.manage.constant;

import lombok.Getter;

public enum BookConditionEnum {
	NEW("NEW"), // mới
	LIKE_NEW("LIKE NEW"), // như mới
	USED("USED"), // đã qua sử dụng(nguyên bản nhưng có dấu hiệu cũ theo thời gian)
	SLIGHT_DAMAGE("SLIGHT DAMAGE"), // hư hại nhẹ (trấy góc , có 1 vài vết mực nhẹ , mất góc nhỏ trang nhưng không
									// ảnh hưởng nội dung)
	DAMAGED("DAMAGED"), // bị hư hại nặng(nhàu nát , mất trang,...)
	TOO_OLD("TOO OLD"), // sách đã quá cũ hoặc hư hại, cần thanh lý hoặc lưu kho
	LOST("LOST"); // mất

	@Getter
	private final String value;
	private BookConditionEnum(String value) {
		this.value = value;
	}
}
