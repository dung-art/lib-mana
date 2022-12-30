package com.lib.manage.dto.home;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewlyImportedBook {
	private String bookName;
	private Integer amount;
	private Double price;
	private Date dateInput;
}
