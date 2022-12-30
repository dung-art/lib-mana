package com.lib.manage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lib.manage.entity.PartnerUser;

@Transactional
@Repository
public interface PartnerUserRepo extends JpaRepository<PartnerUser, String>, JpaSpecificationExecutor<PartnerUser> {
	@Query(value = "Select s from PartnerUsers s Where s.isDelete = false")
	List<PartnerUser> findAllNoDelete();

	@Query(value = "Select s from PartnerUsers s Where s.isDelete = true")
	List<PartnerUser> findAllDeleted();

	@Query(value = "Select s from PartnerUsers s Where s.identifyNumber = :identifyNumber and s.isDelete = false")
	Optional<PartnerUser> findByIdentifyNumber(String identifyNumber);
}
