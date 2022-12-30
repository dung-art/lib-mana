package com.lib.manage.service;

import java.sql.SQLException;
import java.util.List;

import com.lib.manage.dto.ListProductCode;
import com.lib.manage.dto.ProductDto;
import com.lib.manage.dto.request.AdvanceSearchProductRequest;
import com.lib.manage.dto.request.CreateProductRequest;
import com.lib.manage.dto.request.UpdateProductRequest;
import com.lib.manage.dto.response.SuccessResponse;

public interface ProductService {

	SuccessResponse create(CreateProductRequest request) throws Exception;

	SuccessResponse updateAllField(String productCode, UpdateProductRequest request) throws Exception;

	List<ProductDto> findAll();

	SuccessResponse softDeleteProducts(ListProductCode productCodes);

	SuccessResponse advanceSearch(AdvanceSearchProductRequest request) throws SQLException, Exception;

}
