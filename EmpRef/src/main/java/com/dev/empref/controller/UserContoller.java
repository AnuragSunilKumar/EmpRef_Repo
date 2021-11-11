package com.dev.empref.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dev.empref.dao.ApplicationRepository;
import com.dev.empref.dao.JobRepository;
import com.dev.empref.dao.UserRepository;
import com.dev.empref.entities.Applications;
import com.dev.empref.entities.Jobs;
import com.dev.empref.entities.User;
import com.dev.empref.helper.Message;

@Controller
@RequestMapping("/user")
public class UserContoller{
	

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;

	// method for adding common data to response
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
         List<Jobs> jobs=this.jobRepository.findAll();
    	
    	model.addAttribute("jobs",jobs);
		return "employee/employee_dashboard";
	}
	
	// open add form handler
		@GetMapping("/add-refferal")
		public String openAddRefferalForm(Model model) {
			model.addAttribute("title", "Add Refferal");
			model.addAttribute("applications", new Applications());

			return "employee/add_application_form";
		}
		

		// processing add contact form
		@PostMapping("/process-application")
		public String processContact(@ModelAttribute Applications applications,
				/* @RequestParam("Resume") */ MultipartFile file,
				Principal principal, HttpSession session) {

			try {

				String name = principal.getName();
				User user = this.userRepository.getUserByUserName(name);

				// processing and uploading file..

			/*
			 * if (file.isEmpty()) { // if the file is empty then try our message
			 * System.out.println("File is empty"); applications.setResume("contact.png");
			 * 
			 * }
			 */ /*
				 * else { // file the file to folder and update the name to contact
				 * applications.setResume(file.getOriginalFilename());
				 * 
				 * File saveFile = new ClassPathResource("static/img").getFile();
				 * 
				 * Path path = Paths.get(saveFile.getAbsolutePath() + File.separator +
				 * file.getOriginalFilename());
				 * 
				 * Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				 * 
				 * System.out.println("Image is uploaded");
				 * 
				 * }
				 */

				user.getApplications().add(applications);

				applications.setUser(user);

				this.userRepository.save(user);

				System.out.println("DATA " + applications);

				System.out.println("Added to data base");

				// message success.......
				session.setAttribute("message", new Message("Your Refferal is added !! Add more..", "success"));

			} catch (Exception e) {
				System.out.println("ERROR " + e.getMessage());
				e.printStackTrace();
				// message error
				session.setAttribute("message", new Message("Some went wrong !! Try again..", "danger"));

			}

			return "employee/add_application_form";
		}
		
		@RequestMapping("/show-applications")
	    public String showjobs(Model model, Principal principal)
	    {
	    	model.addAttribute("title","Your Refferals");
	    	
	    	String userName = principal.getName();

			User user = this.userRepository.getUserByUserName(userName);
			
	    	
	    	List<Applications> applications=this.applicationRepository.findApplicationsByUser(user.getId());
	    	
	    	model.addAttribute("applications",applications);
	    	
	        return "employee/show-applications";
	    }

}