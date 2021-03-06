package com.dev.empref.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dev.empref.entities.Jobs;
import com.dev.empref.entities.User;
import com.dev.empref.helper.Message;
import com.dev.empref.dao.JobRepository;
import com.dev.empref.dao.UserRepository;


@Controller
public class HomeController {
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JobRepository jobRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
    

	@RequestMapping("/")
    public String home(Model model)
    {
    	model.addAttribute("title","Home-Employee Refferal Program");
        return "home";
    }
    
    @RequestMapping("/show-jobs")
    public String showjobs(Model model)
    {
    	model.addAttribute("title","Available Jobs");
    	
    	List<Jobs> jobs=this.jobRepository.findAll();
    	
    	model.addAttribute("jobs",jobs);
    	
        return "show-jobs";
    }
    
    @RequestMapping("/signin")
    public String login(Model model)
    {
    	model.addAttribute("title","Login");
        return "login";
    }
    
    @RequestMapping("/signup")
    public String signup(Model model)
    {
    	model.addAttribute("title","Register");
    	model.addAttribute("user", new User());
        return "signup";
    }
    
    
    
    @RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, BindingResult result1,
			@RequestParam(value = "agreement", defaultValue = "false") boolean agreement, Model model,
			HttpSession session) {

		try {

			if (!agreement) {
				System.out.println("You have not agreed the terms and conditions");
				throw new Exception("You have not agreed the terms and conditions");
			}

			if (result1.hasErrors()) {
				System.out.println("ERROR " + result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("Agreement " + agreement);
			System.out.println("USER " + user);

			User result = this.userRepository.save(user);

			model.addAttribute("user", new User());

			session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));
			return "signup";

		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something Went wrong !! " + e.getMessage(), "alert-danger"));
			return "signup";
		}

	}
     
   
	
}
