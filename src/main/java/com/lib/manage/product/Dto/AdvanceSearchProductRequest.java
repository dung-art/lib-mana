package com.lib.manage.product.Dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceSearchProductRequest {

	private Boolean searchWay;

	private Boolean isMaterial;

	private String name;

	private List<String> productType;

	private List<String> producer;

	private Float priceUp;

	private Float priceDown;

}
