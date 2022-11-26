package com.lib.manage.product.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductRequest {
	private String productCode;
	private String productName;
	private String productType;
	private String producer;
	private float price;
	private Boolean isMaterial;
}
