package com.lib.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.lib.manage.entity.UserInfo;

@Repository
public interface UserInfoRepository  extends JpaRepository<UserInfo, String>, JpaSpecificationExecutor<UserInfo>{

}
