package com.lib.manage.map;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lib.manage.dto.ProductDto;
import com.lib.manage.dto.request.UpdateProductRequest;
import com.lib.manage.entity.Product;

@Mapper
public interface MapperP {

	@Mapping(target = "dateAdd", ignore = true)
	@Mapping(target = "id", ignore = true)
	Product getProductFromProductDto(ProductDto productDto);
//	ProductDto getProductDtoFromProduct(Product product);
	@Mapping(target = "dateAdd", ignore = true)
	@Mapping(target = "isDelete", ignore = true)
	@Mapping(target = "modifiedDate", ignore = true)
	@Mapping(target = "modifiedUser", ignore = true)
//	@Mapping(target = "modifiedAction", ignore = true)
	@Mapping(target = "userAdd", ignore = true)
	@Mapping(target = "productCode", ignore = true)
	@Mapping(target = "id", ignore = true)
	Product getProductFromUpdateProduct(UpdateProductRequest request, @Context Product product);

	Product getProductFromProduct(Product product);
}
