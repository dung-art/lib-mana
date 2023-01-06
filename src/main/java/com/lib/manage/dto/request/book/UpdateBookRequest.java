package com.lib.manage.dto.request.book;

import com.lib.manage.constant.BookConditionEnum;
import com.lib.manage.constant.BookStatusEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookRequest {
	private String bookInfoId;
	private BookConditionEnum bookCondition; // tình trạng sách
	private BookStatusEnum status;
}
