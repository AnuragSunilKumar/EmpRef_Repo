package com.dev.empref.dao;

import com.dev.empref.entities.Applications;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Applications,Integer> {
    
	@Query("from Applications as c where c.user.id = :userId")
	public List<Applications> findApplicationsByUser(@Param("userId") int userId);
    
}
