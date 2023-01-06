package com.lib.manage.dto.request.book;

import java.util.List;

import com.lib.manage.constant.BookConditionEnum;
import com.lib.manage.constant.BookStatusEnum;
import com.lib.manage.dto.DateRange;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvanceSearchBookRequest {
	private List<String> createUser;
	private DateRange createDate;
	private DateRange modifyDate;
	private List<String> modifiedUser;
	private List<BookConditionEnum> bookCondition; // tình trạng sách
	private List<BookStatusEnum> status;
}
