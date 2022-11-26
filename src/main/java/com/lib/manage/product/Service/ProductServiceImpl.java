package com.lib.manage.product.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.jmapper.JMapper;
import com.lib.manage.common.Response.FailResponse;
import com.lib.manage.common.Response.ResponseObject;
import com.lib.manage.common.Response.SuccessMessageResponse;
import com.lib.manage.common.Response.SuccessResponse;
import com.lib.manage.product.Dto.AdvanceSearchProductRequest;
import com.lib.manage.product.Dto.CreateProductRequest;
import com.lib.manage.product.Dto.CreateProductResponse;
import com.lib.manage.product.Dto.ListProductCode;
import com.lib.manage.product.Dto.ProductDto;
import com.lib.manage.product.Dto.SearchProductDto;
import com.lib.manage.product.Dto.UpdateProductRequest;
import com.lib.manage.product.Dto.UpdateProductResponse;
import com.lib.manage.product.Entity.Product;
import com.lib.manage.product.Map.MapProduct;
import com.lib.manage.product.Query.Query;
import com.lib.manage.product.Repository.ProductRepo;

@Transactional
@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	private ProductRepo productRepo;

	@Autowired
	DataSource dataSource;

	private JMapper<ProductDto, Product> mapper = new JMapper<>(ProductDto.class, Product.class);
	private JMapper<Product, CreateProductRequest> cJMapper = new JMapper<>(Product.class, CreateProductRequest.class);

	@Override
	public SuccessResponse create(CreateProductRequest request) throws Exception {
		try {
			Optional<Product> oProduct = productRepo.findByProductCode(request.getProductCode());
			if (oProduct.isPresent()) {
				return new FailResponse("Sản phẩm đã tồn tại !");
			} else {
				Product product = cJMapper.getDestination(request);
				productRepo.save(product);
				return new CreateProductResponse(product.getProductCode(), "Thêm mới sản phẩm thành công !");
			}
		} catch (Exception e) {
		}
		return new FailResponse("Đã có lỗi xãy ra !");
	}

	@Override
	public List<ProductDto> findAll() {
		List<Product> products = productRepo.findAllNoDelete();
		if (products.isEmpty() || products == null) {
			return null;
		}
		List<ProductDto> productDtos = new ArrayList<>();
		for (Product product : products) {
			productDtos.add(mapper.getDestination(product));
		}
		return productDtos;
	}

	@Override
	public SuccessResponse softDeleteProducts(ListProductCode productCodes) {
		List<Product> products = productRepo.findAllById(productCodes.getProductCodes());
		if (products.isEmpty() || products == null) {
			return new FailResponse("Không tìm thấy sản phẩm nào được chỉ định hoặc không có sản phẩm nào được xóa !");
		}
		for (Product product : products) {
			product.setIsDelete(true);
		}
		return new SuccessMessageResponse("Xóa thành công !");
	}

	@Override
	public SuccessResponse updateAllField(String productCode, UpdateProductRequest request) throws Exception {
		try {
			Optional<Product> oProduct = productRepo.findById(productCode);
			if (!oProduct.isPresent()) {
				return new FailResponse("Sản phẩm chưa tồn tại!");
			}
			if (oProduct.get().getIsDelete()) {
				return new FailResponse("Sản phẩm đã bị xóa !");
			} else {
				if (oProduct.get().getProductName().equals(request.getProductName())
						&& oProduct.get().getProductType().equals(request.getProductType())
						&& oProduct.get().getProducer().equals(request.getProducer())
						&& oProduct.get().getPrice().equals(request.getPrice())
						&& oProduct.get().getPathImage().equals(request.getPathImage())
						&& oProduct.get().getIsMaterial().equals(request.getIsMaterial())
						&& oProduct.get().getWeight().equals(request.getWeight())
						&& oProduct.get().getSize().equals(request.getSize())
						&& oProduct.get().getDescription().equals(request.getDescription())) {
					return new FailResponse("Sản phẩm không được sửa !");
				}
				Product product = oProduct.get();
				product = MapProduct.getProductFromUpdateProduct(request, product);
				productRepo.save(product);
				ProductDto dto = mapper.getDestination(product);
				dto.setModifiedDate(LocalDateTime.now());
				return new UpdateProductResponse(dto);
			}
		} catch (Exception e) {
		}
		return new FailResponse("Cập nhật sản phẩm thất bại !");
	}

	@Override
	public SuccessResponse advanceSearch(AdvanceSearchProductRequest request) throws SQLException, Exception {
		String sqlString = Query.QueryLine(request);
		try (Connection connection = dataSource.getConnection()) {
			try (Statement cs = connection.createStatement()) {
				ResultSet rs = cs.executeQuery(sqlString);
				List<SearchProductDto> searchProductDtos = new ArrayList<>();
				while (rs.next()) {
					SearchProductDto dto = new SearchProductDto();
					dto.setProductCode(rs.getString("product_code"));
					dto.setProductName(rs.getString("product_name"));
					dto.setProductType(rs.getString("product_type"));
					dto.setIsMaterial(rs.getBoolean("is_material"));
					dto.setPrice(rs.getFloat("price"));
					dto.setProducer(rs.getString("producer"));
					dto.setPathImage(rs.getString("path_image"));
					dto.setDescription(rs.getString("description"));
					dto.setWeight(rs.getString("weight"));
					dto.setSize(rs.getString("size"));

					searchProductDtos.add(dto);
				}
				return new ResponseObject<>(searchProductDtos);
			} catch (Exception e) {
				throw new Exception(e);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
