package com.lib.manage.dto.home;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardDto {
	private Integer reader;
	private Integer bookAmount;
	private Integer borrowed;
	private Integer violation;
}
