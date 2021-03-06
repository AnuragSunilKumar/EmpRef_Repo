package com.dev.empref.dao;

import com.dev.empref.entities.Applications;
import com.dev.empref.entities.User;

import org.springframework.data.domain.Pageable;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Applications, Integer> {

	//show via pagination
	@Query("from Applications as c where c.user.id = :userId")
	public Page<Applications> findApplicationsByUser(@Param("userId") int userId, Pageable pageable);

	//search
	public List<Applications> findByAppnameContainingAndUser(String appName, User users);
	
	
	

}
