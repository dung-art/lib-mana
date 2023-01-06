package com.lib.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lib.manage.entity.Borrow;
@Repository
public interface BorrowRepository  extends JpaRepository<Borrow, String>, JpaSpecificationExecutor<Borrow>{

}
