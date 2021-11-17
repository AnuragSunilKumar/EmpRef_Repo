package com.dev.empref.services;

import java.util.List;

import com.dev.empref.dao.JobRepository;
import com.dev.empref.entities.Jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JobServices {

    @Autowired
    private JobRepository jobRepository;
   
    // get all jobs
    public List<Jobs> getAllJobs() {
        List<Jobs> list = (List<Jobs>) this.jobRepository.findAll();
        return list;
    }

	/*
	 * // get jobs by ID public Jobs getJobById(int id) { Jobs jobs = null; try {
	 * 
	 * this.jobRepository.findById(id); } catch (Exception e) { e.printStackTrace();
	 * } return jobs; }
	 */

    // add jobs
    public Jobs addjob(Jobs j) {
        Jobs result = jobRepository.save(j);
        return result;
    }

    // delete jobs
    public void deleteJobs(int jid) {
        //list = list.stream().filter(job -> job.getId() != jid).collect(Collectors.toList());
        jobRepository.deleteById(jid);
    }

    // Update job
    public Jobs updateJob(Jobs job, int jobsId) {
        
        job.setId(jobsId);
        Jobs result =jobRepository.save(job);
        return result;
    }
}
