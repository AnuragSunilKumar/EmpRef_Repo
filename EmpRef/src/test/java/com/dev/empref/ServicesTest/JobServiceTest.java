package com.dev.empref.ServicesTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dev.empref.dao.JobRepository;
import com.dev.empref.entities.Jobs;
import com.dev.empref.services.JobServices;

@SpringBootTest
public class JobServiceTest {

	@MockBean
	private JobRepository jobRepository;

	@Autowired
	private JobServices jobServices;

	@Test
	public void addJobTest() {
		Jobs job = new Jobs();
		job.setId(1);
		job.setJobName("Php Developer");
		job.setJobdescription("Php developer");
		job.setJobExp("7-8ys");
		job.setContact("Mayank");

		Mockito.when(jobRepository.save(job)).thenReturn(job);

		Jobs actualResult = jobServices.addjob(job);

		assertThat(actualResult).isEqualTo(job);

	}

	@Test
	public void getAllJobsTest() {

		Jobs job1 = new Jobs();
		job1.setId(1);
		job1.setJobName("Php Developer");
		job1.setJobdescription("Php developer");
		job1.setJobExp("7-8ys");
		job1.setContact("Mayank");

		Jobs job2 = new Jobs();
		job2.setId(2);
		job2.setJobName("Java Developer");
		job2.setJobdescription("Java developer");
		job2.setJobExp("8-9ys");
		job2.setContact("Sumanth");

		List<Jobs> jobsList = new ArrayList<>();

		jobsList.add(job1);
		jobsList.add(job2);

		Mockito.when(jobRepository.findAll()).thenReturn(jobsList);

		List<Jobs> actualResult = jobServices.getAllJobs();

		assertThat(actualResult).isEqualTo(jobsList);
		

	}

	@Test
	public void updateJobsTest() {
		Jobs job = new Jobs();
		job.setId(1);
		job.setJobName("Php Developer");
		job.setJobdescription("Php developer");
		job.setJobExp("7-8ys");
		job.setContact("Mayank");

		Mockito.when(jobRepository.findById(1)).thenReturn(job);

		job.setContact("Sumanth");
		Mockito.when(jobRepository.save(job)).thenReturn(job);

		Jobs actualResult = jobServices.updateJob(job, 1);

		assertThat(actualResult).isEqualTo(job);

	}

	@Test
	public void deleteJobsTest() {
		Jobs job = new Jobs();
		job.setId(1);
		job.setJobName("Php Developer");
		job.setJobdescription("Php developer");
		job.setJobExp("7-8ys");
		job.setContact("Mayank");

		Mockito.when(jobRepository.findById(1)).thenReturn(job);
		
		boolean actualResult = jobRepository.existsById(job.getId());
		
		Mockito.when(actualResult).thenReturn(false);

		assertThat(actualResult);
	}
}
