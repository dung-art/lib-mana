package com.lib.manage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lib.manage.constant.AccountTypeEnum;
import com.lib.manage.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, String>, JpaSpecificationExecutor<Account> {

	Optional<Account> findByAccountName(String accountName);

	@Modifying
	@Query(nativeQuery = true, value = "update lb_account set is_deleted = true where id in (:ids)")
	int blockAccountByIds(@Param("ids") List<String> ids);

	Page<Account> findByAccountType(AccountTypeEnum accountType, Pageable pageable);

	@Modifying
	@Query("update Account u set u.password = :password where u.id = :id")
	int updatePassword(@Param("password") String pwd, @Param("id") String id);

	@Modifying
	@Query("update Account u set u.isDisable = :isDisable where u.id = :id")
	int updateIsActive(@Param("isDisable")boolean isDisable, String id);
}
