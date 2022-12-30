package com.lib.manage.dto.reader;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReaderInfoDto {
	private String readerId;
	private String readerName;
	private String cardId;
	private Boolean gender;
	private Date birthDate;
	private String status;
}
