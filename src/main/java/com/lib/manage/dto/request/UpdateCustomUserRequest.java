package com.lib.manage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomUserRequest {

	private String fullName;

	private String identifyNumber;

	private String address;

	// dd/MM/yyyy
	private String birthDate;

	private String pathImageUser;

	private String description;

}
