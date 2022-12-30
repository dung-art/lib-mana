package com.lib.manage.dto;

import java.time.LocalDateTime;
import com.googlecode.jmapper.annotations.JGlobalMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JGlobalMap
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private Boolean isDelete;
	private String userAdd;
	private LocalDateTime dateAdd;
	private LocalDateTime modifiedDate;
	private String modifiedUser;
//	private String modifiedAction;
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
