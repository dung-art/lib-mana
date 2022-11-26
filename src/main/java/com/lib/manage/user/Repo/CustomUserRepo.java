package com.lib.manage.user.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lib.manage.user.Entity.CustomUser.CustomUser;

@Transactional
@Repository
public interface CustomUserRepo extends JpaRepository<CustomUser, String>, JpaSpecificationExecutor<CustomUser> {
	@Query(value = "Select s from CustomUsers s Where s.isDelete = false")
	List<CustomUser> findAllNoDelete();

	@Query(value = "Select s from CustomUsers s Where s.isDelete = true")
	List<CustomUser> findAllDeleted();

	@Query(value = "Select s from CustomUsers s Where s.identifyNumber = :identifyNumber and s.isDelete = false")
	Optional<CustomUser> findByIdentifyNumber(@Param("identifyNumber") String identifyNumber);
	
	@Query(value = "Select s from CustomUsers s Where s.userName = :userName")
	Optional<CustomUser> findByUserName(@Param("userName") String userName);
}