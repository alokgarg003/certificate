package com.cdac.repository;

import com.cdac.model.Certificate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {

	@Query("select  c from Certificate as c where c.userId = :userId")
	List<Certificate> findByUserId(Long userId);

	@Query("SELECT   "
			+ "    c  "
			+ "FROM  "
			+ "    Certificate AS c  "
			+ "WHERE  "
			+ "    c.userId != (SELECT   "
			+ "            u.id  "
			+ "        FROM  "
			+ "            User AS u  "
			+ "        WHERE  "
			+ "            u.username = 'admin')")
	List<Certificate> getAllCertificatesAdmin();
	
}