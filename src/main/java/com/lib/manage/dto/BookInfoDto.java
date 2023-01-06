package com.lib.manage.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookInfoDto {
	private String id;
	private String createUser;
	private String createDate;
	private String modifyDate;
	private String modifiedUser;
	private String modifyAction;
	private String bookName;
	private String category;
	private String auth;
	private Float price;
	private String bookImage;
	private String publisher;
	private String description;
}
