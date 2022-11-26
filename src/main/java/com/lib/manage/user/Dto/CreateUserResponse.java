package com.lib.manage.user.Dto;

import com.lib.manage.common.Response.CreateSuccessResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponse extends CreateSuccessResponse {
	private static final long serialVersionUID = 1L;
	private String message;

	public CreateUserResponse(String id, String message) {
		super(id);
		this.message = message;
	}
}
