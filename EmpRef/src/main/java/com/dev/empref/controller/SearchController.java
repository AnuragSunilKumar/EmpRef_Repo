package com.dev.empref.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dev.empref.dao.ApplicationRepository;
import com.dev.empref.dao.UserRepository;
import com.dev.empref.entities.Applications;
import com.dev.empref.entities.User;

@RestController
public class SearchController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	// search handler
	@GetMapping("/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String query, Principal principal) {
		System.out.println(query);
		User user = this.userRepository.getUserByUserName(principal.getName());

		List<Applications> applications = this.applicationRepository.findByAppnameContainingAndUser(query, user);

		return ResponseEntity.ok(applications);
	}

}