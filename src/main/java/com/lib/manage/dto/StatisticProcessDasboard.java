package com.lib.manage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatisticProcessDasboard {
	private long statisticUser; // người dùng
	private long statisticBorowingUser; // hiện đang mượn
	private long statisticBorowOutDateUser;	// hiện đang mượn và quá hạn
	private long statisticUserLostBooks;// người dùng báo làm mất sách	
	private long statisticForbiddenUser;// hiện đang bị cấm mượn

	
	private long statisticAmountBookBorowing; // số sách đang được mượn
	private long statisticAmountBookBorowOutDate; // số sách mượn quá hạn
	private long statisticAmountBookLost;	 // số sách bị thất lạc
}
