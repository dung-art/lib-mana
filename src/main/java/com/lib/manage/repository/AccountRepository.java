package com.lib.manage.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lib.manage.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {
    
	Account findByAccountName(String accountName);

    @Query(value = "update user_role set role_id = ?1 where user_id = ?2", nativeQuery = true)
    Account updateRole(Long roleId, Long userId);

    @Query(value = "select * from users", nativeQuery = true)
    Iterable<Account> checkUser();
}
