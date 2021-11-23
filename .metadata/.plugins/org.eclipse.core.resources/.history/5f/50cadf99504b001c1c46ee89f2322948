package com.dev.empref.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dev.empref.dao.ApplicationRepository;
import com.dev.empref.dao.JobRepository;
import com.dev.empref.dao.UserRepository;
import com.dev.empref.entities.Jobs;
import com.dev.empref.entities.User;

@Controller
@RequestMapping("/admin")
public class AdminController {

	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("USERNAME " + userName);

		// get the user using usernamne(Email)

		User user = userRepository.getUserByUserName(userName);
		System.out.println("USER " + user);
		model.addAttribute("user", user);

	}
	
	// dashboard home
		@RequestMapping("/index")
		public String dashboard(Model model, Principal principal) {
			model.addAttribute("title", "User Dashboard");
			List<Jobs> jobs = this.jobRepository.findAll();

			model.addAttribute("jobs", jobs);
			return "admin/admin_dashboard";
		}

}
