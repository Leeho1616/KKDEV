package com.webapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.webapp.model.UsersModel;
import com.webapp.service.UsersService;

@Controller
public class UsersController {
	
	@Autowired
	private final UsersService usersService;
	
	public UsersController(UsersService usersService) {
		super();
		this.usersService = usersService;
	}

	@GetMapping("/register")
	public String getRegisterPage(Model model) {
		model.addAttribute("registerRequest", new UsersModel());
		return "register_page";
	}
	
	@GetMapping("/login")
	public String getLoginPage(Model model) {
		model.addAttribute("loginRequest", new UsersModel());
		return "login_page";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute UsersModel usersModel) {
		System.out.println("register request" + usersModel);

		UsersModel registeredUser = usersService.registerUser(usersModel.getFirstname_hina(), usersModel.getLastname_hina(), usersModel.getFirstname_kata(), usersModel.getLastname_kata(), usersModel.getEmail(), usersModel.getPassword(),usersModel.getBirthday(), usersModel.getAddress1(), usersModel.getAddress2(), usersModel.getAddress3(), usersModel.getNationality(), usersModel.getSex());

		return (registeredUser == null) ? "error_page" : "redirect:/login";
	}
	 
	@PostMapping("/login") 
	public String login(@ModelAttribute UsersModel usersModel, Model model) {
		System.out.println("login request" + usersModel);

		UsersModel authenticated = usersService.authenticate(usersModel.getEmail(), usersModel.getPassword());
		if(authenticated != null) {
			model.addAttribute("userEmail", authenticated.getEmail());
			return "main_page";
		} else {
			return "error_page";
		}
	}
	
	
}
