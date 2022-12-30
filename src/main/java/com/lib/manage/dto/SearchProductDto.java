package com.lib.manage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductDto {
	private String productCode;
	private String productName;
	private String productType;
	private String producer;
	private Float price;
	private String pathImage;
	private Boolean isMaterial;
	private String weight;
	private String size;
	private String description;
}
