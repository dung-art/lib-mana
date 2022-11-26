package com.lib.manage.product.Ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lib.manage.common.Response.FailResponse;
import com.lib.manage.common.Response.ResponseObject;
import com.lib.manage.common.Response.SuccessResponse;
import com.lib.manage.product.Dto.AdvanceSearchProductRequest;
import com.lib.manage.product.Dto.CreateProductRequest;
import com.lib.manage.product.Dto.ListProductCode;
import com.lib.manage.product.Dto.ProductDto;
import com.lib.manage.product.Dto.UpdateProductRequest;
import com.lib.manage.product.Service.ProductService;

@RestController
@Transactional
@RequestMapping("/api/v1/products")
public class ProductCtrl {
	@Autowired
	private ProductService productService;

	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse create(@RequestBody CreateProductRequest request) throws Exception {
		return productService.create(request);
	}

	@GetMapping("/all-product")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse findAll() {
		List<ProductDto> dtos = productService.findAll();
		if (dtos == null || dtos.isEmpty()) {
			return new FailResponse("Không tìm thấy sản phẩm nào hoặc các sản phẩm đã được xóa !");
		}
		return new ResponseObject<>(dtos);
	}

	@PostMapping("/delete-products")
	@ResponseStatus(HttpStatus.OK)
	public SuccessResponse deleteProduct(@RequestBody ListProductCode productCodes) {
		return productService.softDeleteProducts(productCodes);
	}

	@PostMapping("/{id}/update-product")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SuccessResponse updateAllField(@PathVariable("id") String productCode,
			@RequestBody UpdateProductRequest request) throws Exception {
		return productService.updateAllField(productCode, request);
	}

	@PostMapping("/advance-search")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public SuccessResponse advanceSearch(@RequestBody AdvanceSearchProductRequest request) throws Exception {
		return productService.advanceSearch(request);
	}

}
