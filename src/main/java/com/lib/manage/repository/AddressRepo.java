package com.lib.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lib.manage.entity.Address;

@Transactional
@Repository
public interface AddressRepo extends JpaRepository<Address, String>, JpaSpecificationExecutor<Address> {

}
