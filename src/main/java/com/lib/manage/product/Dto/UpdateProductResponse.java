package com.lib.manage.product.Dto;

import java.io.Serializable;

import com.lib.manage.common.Response.SuccessResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateProductResponse extends SuccessResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private ProductDto data;

}
