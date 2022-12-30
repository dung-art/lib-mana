package com.lib.manage.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateProductResponse extends CreateSuccessResponse {

	private static final long serialVersionUID = 1L;
	private String message;

	public CreateProductResponse(String id, String message) {
		super(id);
		this.message = message;
	}

}