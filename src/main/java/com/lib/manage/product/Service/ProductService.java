package com.lib.manage.product.Service;

import java.sql.SQLException;
import java.util.List;

import com.lib.manage.common.Response.SuccessResponse;
import com.lib.manage.product.Dto.AdvanceSearchProductRequest;
import com.lib.manage.product.Dto.CreateProductRequest;
import com.lib.manage.product.Dto.ListProductCode;
import com.lib.manage.product.Dto.ProductDto;
import com.lib.manage.product.Dto.UpdateProductRequest;

public interface ProductService {

	SuccessResponse create(CreateProductRequest request) throws Exception;

	SuccessResponse updateAllField(String productCode, UpdateProductRequest request) throws Exception;

	List<ProductDto> findAll();

	SuccessResponse softDeleteProducts(ListProductCode productCodes);

	SuccessResponse advanceSearch(AdvanceSearchProductRequest request) throws SQLException, Exception;

}
