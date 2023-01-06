package com.lib.manage.dto.request.book;

import com.lib.manage.constant.BookConditionEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookRequest {
	private String bookInfoId;
	private BookConditionEnum bookCondition; // tình trạng sách
}
