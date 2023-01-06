package com.lib.manage.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lib.manage.entity.Account;
import com.lib.manage.entity.Token;

@Transactional
@Repository
public interface TokenRepo extends JpaRepository<Token, String>, JpaSpecificationExecutor<Account> {
	Optional<Token> findByAccountIdAndValue(String name, String value);

	@Query(value = "delete from Token Where accountId =:accountId")
	int removeToken(String accountId);
}