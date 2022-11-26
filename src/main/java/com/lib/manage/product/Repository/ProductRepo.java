package com.lib.manage.product.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lib.manage.product.Entity.Product;

@Transactional
@Repository
public interface ProductRepo extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {

	Optional<Product> findByProductCode(String productCode);
	
	@Query(value = "Select p from Products p Where p.isDelete = false")
	List<Product> findAllNoDelete();

	@Query(value = "Select p from Products p Where p.isDelete = true")
	List<Product> findAllDeleted();

	@Query(value = ":query", nativeQuery = true)
	List<Product> advanceSearchCommon(@Param("query") String request);
}
