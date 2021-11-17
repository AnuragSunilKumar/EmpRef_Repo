package com.dev.empref.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.empref.entities.Jobs;

@Repository

public interface JobRepository extends JpaRepository <Jobs,Integer>{
    public Jobs findById(int id);
    

    
    
}
