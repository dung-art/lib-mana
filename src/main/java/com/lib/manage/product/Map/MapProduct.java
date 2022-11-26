package com.lib.manage.product.Map;

import com.lib.manage.product.Dto.ProductDto;
import com.lib.manage.product.Dto.UpdateProductRequest;
import com.lib.manage.product.Entity.Product;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MapProduct {

	public ProductDto getProductDtoFromProduct(Product product) {
		if (product == null) {
			return null;
		}

		ProductDto productDto = new ProductDto();

		productDto.setDateAdd(product.getDateAdd());
		productDto.setDescription(product.getDescription());
		productDto.setIsDelete(product.getIsDelete());
		productDto.setIsMaterial(product.getIsMaterial());
//		productDto.setModifiedAction(product.getModifiedAction());
		productDto.setModifiedDate(product.getModifiedDate());
		productDto.setModifiedUser(product.getModifiedUser());
		productDto.setPathImage(product.getPathImage());
		productDto.setPrice(product.getPrice());
		productDto.setProducer(product.getProducer());
		productDto.setProductCode(product.getProductCode());
		productDto.setProductName(product.getProductName());
		productDto.setProductType(product.getProductType());
		productDto.setSize(product.getSize());
		productDto.setUserAdd(product.getUserAdd());
		productDto.setWeight(product.getWeight());

		return productDto;
	}

	public static Product getProductFromProductDto(ProductDto productDto) {
		if (productDto == null) {
			return null;
		}

		Product product = new Product();

		product.setIsDelete(productDto.getIsDelete());
//		product.setModifiedAction(productDto.getModifiedAction());
		product.setModifiedDate(productDto.getModifiedDate());
		product.setModifiedUser(productDto.getModifiedUser());
		product.setUserAdd(productDto.getUserAdd());
		product.setDescription(productDto.getDescription());
		product.setIsMaterial(productDto.getIsMaterial());
		product.setPathImage(productDto.getPathImage());
		product.setPrice(productDto.getPrice());
		product.setProducer(productDto.getProducer());
		product.setProductCode(productDto.getProductCode());
		product.setProductName(productDto.getProductName());
		product.setProductType(productDto.getProductType());
		product.setSize(productDto.getSize());
		product.setWeight(productDto.getWeight());

		return product;
	}

	public static Product getProductFromUpdateProduct(UpdateProductRequest request, Product product) {
		if (request == null) {
			return null;
		}
		if (request.getDescription() != null) {
			product.setDescription(request.getDescription());
		}
		if (request.getIsMaterial() != null) {
			product.setIsMaterial(request.getIsMaterial());
		}
		if (request.getPathImage() != null) {
			product.setPathImage(request.getPathImage());
		}
		if (request.getPrice() != null) {
			product.setPrice(request.getPrice());
		}
		if (request.getProducer() != null) {
			product.setProducer(request.getProducer());
		}
		if (request.getProductName() != null) {
			product.setProductName(request.getProductName());
		}
		if (request.getProductType() != null) {
			product.setProductType(request.getProductType());
		}
		if (request.getSize() != null) {
			product.setSize(request.getSize());
		}
		if (request.getWeight() != null) {
			product.setWeight(request.getWeight());
		}

		return product;
	}
}
