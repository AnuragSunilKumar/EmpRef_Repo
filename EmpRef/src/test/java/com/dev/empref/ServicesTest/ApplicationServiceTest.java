package com.dev.empref.ServicesTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.dev.empref.dao.ApplicationRepository;
import com.dev.empref.entities.Applications;
import com.dev.empref.services.ApplicationServices;

@SpringBootTest
public class ApplicationServiceTest {

	@MockBean
	private ApplicationRepository applicationRepository;

	@Autowired
	private ApplicationServices applicationServices;

	@Test
	public void getAllApplicationsTest() {

		Applications app1 = new Applications();
		app1.setId(1);
		app1.setAppname("Anurag");
		app1.setApp_number("123456789");
		app1.setApp_for("Java Developer");
		app1.setApp_email("anurag123@gmail.com");

		Applications app2 = new Applications();
		app2.setId(2);
		app2.setAppname("Aryan");
		app2.setApp_number("22446688");
		app2.setApp_for("PHP Developer");
		app2.setApp_email("aryan@gmail.com");

		List<Applications> appsList = new ArrayList<>();

		appsList.add(app1);
		appsList.add(app2);

		Mockito.when(applicationRepository.findAll()).thenReturn(appsList);

		List<Applications> actualResult = applicationServices.getAllApplications();

		assertThat(actualResult);

	}

	@Test
	public void addApplicationTest() {
		Applications app1 = new Applications();
		app1.setId(1);
		app1.setAppname("Anurag");
		app1.setApp_number("123456789");
		app1.setApp_for("Java Developer");
		app1.setApp_email("anurag123@gmail.com");


		Mockito.when(applicationRepository.save(app1)).thenReturn(app1);

		Applications actualResult = applicationServices.addApplications(app1);

		assertThat(actualResult).isEqualTo(app1);

	}

	@Test
	public void updateApplicationTest() {
		Applications app1 = new Applications();
		app1.setId(1);
		app1.setAppname("Anurag");
		app1.setApp_number("123456789");
		app1.setApp_for("Java Developer");
		app1.setApp_email("anurag123@gmail.com");

		Mockito.when(applicationRepository.getById(1)).thenReturn(app1);

		app1.setApp_number("9845744667");

		Mockito.when(applicationRepository.save(app1)).thenReturn(app1);

		Applications actualResult = applicationServices.updateApplications(app1, 1);

		assertThat(actualResult).isEqualTo(app1);

	}

	@Test
	public void deleteApplicationTest() {

		Applications app1 = new Applications();
		
		app1.setId(1);
		app1.setAppname("Anurag");
		app1.setApp_number("123456789");
		app1.setApp_for("Java Developer");
		app1.setApp_email("anurag123@gmail.com");

		Mockito.when(applicationRepository.getById(1)).thenReturn(app1);
		
		boolean actualResult = applicationRepository.existsById(app1.getId());
		
		Mockito.when(actualResult).thenReturn(false);
		
		assertThat(actualResult);

	}

}
