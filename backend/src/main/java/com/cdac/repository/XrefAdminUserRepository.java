package com.cdac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.model.XrefAdminUser;

@Repository
public interface XrefAdminUserRepository  extends JpaRepository<XrefAdminUser, Long>{

	@Query("select u.userId from XrefAdminUser as u where u.adminId = :userIdFromToken")
	List<Long> findByAdminId(Long userIdFromToken);

}
