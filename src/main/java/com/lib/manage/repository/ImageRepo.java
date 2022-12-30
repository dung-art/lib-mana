package com.lib.manage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lib.manage.entity.Image;

@Transactional
@Repository
public interface ImageRepo extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image> {

	@Query("select i from Images i where i.id = :id")
	Optional<Image> findById(@Param("id") String id);
	
	Optional<Image> findByImageCode(@Param("imageCode") String imageCode);
	Optional<Image> findByImageName(@Param("imageName") String imageName);

}
