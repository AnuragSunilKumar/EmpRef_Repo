package com.dev.empref;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.dev.empref.dao")
public class EmpRefApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpRefApplication.class, args);
	}

}
