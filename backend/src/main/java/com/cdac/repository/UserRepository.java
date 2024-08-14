package com.cdac.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cdac.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    User findByUsername(String username);


    @Query("select u from User u where u.id in :userIds")
    List<User> findByUserIds(@Param("userIds") List<Long> userIds);
}
