package com.lib.manage.user.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lib.manage.user.Entity.CustomUser.Address;

@Transactional
@Repository
public interface AddressRepo extends JpaRepository<Address, String>, JpaSpecificationExecutor<Address> {

}
