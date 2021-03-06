package com.dev.empref.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class UserContoller {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private ApplicationRepository applicationRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// method for adding common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		// System.out.println("USERNAME " + userName);

		// get the user using usernamne(Email)

		User user = userRepository.getUserByUserName(userName);
		// System.out.println("USER " + user);
		model.addAttribute("user", user);

	}

	// dashboard home
	@RequestMapping("/index")
	public String dashboard(Model model, Principal principal) {
		model.addAttribute("title", "User Dashboard");
		List<Jobs> jobs = this.jobRepository.findAll();

		model.addAttribute("jobs", jobs);
		return "employee/employee_dashboard";
	}

	// open add form handler
	@GetMapping("/add-refferal")
	public String openAddRefferalForm(Model model) {

		List<Jobs> jobsList = this.jobRepository.findAll();

		model.addAttribute("jobsList", jobsList);
		model.addAttribute("title", "Add Refferal");
		model.addAttribute("applications", new Applications());

		return "employee/add_application_form";
	}

	// processing add refferal form
	@PostMapping("/process-application")
	public String processApplications(@ModelAttribute Applications applications,
			@RequestParam("profileResume") MultipartFile file, Principal principal, HttpSession session) {

		try {

			String name = principal.getName();
			User user = this.userRepository.getUserByUserName(name);

			// processing and uploading file..

			if (file.isEmpty()) { // if the file is empty then try our message
				// System.out.println("File is empty");
				// applications.setResume("contact.png");

			}

			else {
				// file the file to folder and update the name to contact
				applications.setResume(file.getOriginalFilename());

				File saveFile = new ClassPathResource("static/resume1").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				// System.out.println("Resume is uploaded");

			}

			user.getApplications().add(applications);

			applications.setUser(user);

			this.userRepository.save(user);

			// System.out.println("DATA " + applications);

			// System.out.println("Added to data base");

			// message success.......
			session.setAttribute("message", new Message("Your Refferal is added !! Add more..", "success"));

		} catch (Exception e) {
			// System.out.println("ERROR " + e.getMessage());
			e.printStackTrace();
			// message error
			session.setAttribute("message", new Message("Some went wrong !! Try again..", "danger"));

		}

		return "redirect:/user/add-refferal";
	}

	// show applications handler
	// per page = 5[n]
	// current page = 0 [page]
	@GetMapping("/show-applications/{page}")
	public String showApplications(@PathVariable("page") Integer page, Model m, Principal principal) {
		m.addAttribute("title", "Show Refferals");
		// applicants ki list ko bhejni hai

		String userName = principal.getName();

		User user = this.userRepository.getUserByUserName(userName);

		// currentPage-page
		// applicants Per page - 5
		Pageable pageable = PageRequest.of(page, 5);

		Page<Applications> applications = this.applicationRepository.findApplicationsByUser(user.getId(), pageable);

		m.addAttribute("applications", applications);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", applications.getTotalPages());

		return "employee/show-applications";
	}

	// showing particular contact details.

	@RequestMapping("/{id}/refferal")
	public String showContactDetail(@PathVariable("id") Integer id, Model model, Principal principal) {
		// System.out.println("CID " + id);

		Optional<Applications> applicationOptional = this.applicationRepository.findById(id);
		Applications applications = applicationOptional.get();

		//
		String userName = principal.getName();
		User user = this.userRepository.getUserByUserName(userName);

		if (user.getId() == applications.getUser().getId()) {
			model.addAttribute("applications", applications);
			model.addAttribute("title", applications.getAppname());
		}

		return "employee/refferal_detail";
	}

	@GetMapping("/delete/{id}")
	@Transactional
	public String deleteContact(@PathVariable("id") Integer id, Model model, HttpSession session, Principal principal) {
		// System.out.println("CID " + id);

		Applications applications = this.applicationRepository.findById(id).get();

		// delete old photo

		User user = this.userRepository.getUserByUserName(principal.getName());

		user.getApplications().remove(applications);

		this.userRepository.save(user);

		// System.out.println("DELETED");
		session.setAttribute("message", new Message("Refferal deleted succesfully...", "success"));

		return "redirect:/user/show-applications/0";
	}

	// open update form handler
	@PostMapping("/update-refferal/{id}")
	public String updateForm(@PathVariable("id") Integer id, Model m) {

		m.addAttribute("title", "Update Refferal");

		Applications applications = this.applicationRepository.findById(id).get();

		m.addAttribute("applications", applications);

		List<Jobs> jobsList = this.jobRepository.findAll();

		m.addAttribute("jobsList", jobsList);

		return "employee/update_form";
	}

	// update Refferal handler
	@RequestMapping(value = "/process-update", method = RequestMethod.POST)
	public String updateHandler(@ModelAttribute Applications applications,
			@RequestParam("profileResume") MultipartFile file, Model m, HttpSession session, Principal principal) {

		try {

			// old contact details
			Applications oldapplicationDetail = this.applicationRepository.findById(applications.getId()).get();

			// image..
			if (!file.isEmpty()) { // file work.. // rewrite

				// delete old resume

				File deleteFile = new ClassPathResource("static/img").getFile();
				File file1 = new File(deleteFile, oldapplicationDetail.getResume());
				file1.delete();

				// update new resume

				File saveFile = new ClassPathResource("static/img").getFile();

				Path path = Paths.get(saveFile.getAbsolutePath() + File.separator + file.getOriginalFilename());

				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				applications.setResume(file.getOriginalFilename());

			} else {
				applications.setResume(oldapplicationDetail.getResume());
			}

			User user = this.userRepository.getUserByUserName(principal.getName());

			applications.setUser(user);

			this.applicationRepository.save(applications);

			session.setAttribute("message", new Message("Your Refferal is updated...", "success"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		// System.out.println(" NAME " + applications.getAppname());
		// System.out.println(" ID " + applications.getId());
		return "redirect:/user/show-applications/0";
	}

	// your profile handler
	@GetMapping("/profile")
	public String yourProfile(Model model) {
		model.addAttribute("title", "Profile Page");
		return "employee/profile";
	}

	@GetMapping("/settings")
	public String openSettings(Model m, Principal principal) {
		String userName = principal.getName();
		// System.out.println("USERNAME " + userName);

		// get the user using usernamne(Email)

		User user = userRepository.getUserByUserName(userName);
		// System.out.println("USER " + user);
		m.addAttribute("user", user);
		m.addAttribute("title", "Settings");

		
		return "employee/settings";
	}
	
	
	
	

	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {
		
		String userName = principal.getName();
		User currentUser = this.userRepository.getUserByUserName(userName);
		

		if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
			// change the password

			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepository.save(currentUser);
			session.setAttribute("message", new Message("Your password is successfully changed..", "success"));

		} else {
			// error...
			session.setAttribute("message", new Message("Please Enter correct old password !!", "danger"));
			return "redirect:/user/settings";
		}

		return "redirect:/user/index";
	}
	
	

}