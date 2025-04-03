package web.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;


import java.util.ArrayList;
import java.util.List;

@ComponentScan("web")
@Controller
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(value = "/")
	public String printWelcome(ModelMap model) {
		List<String> messages = new ArrayList<>();
		messages.add("Hello!");
		messages.add("I'm Spring MVC application");
		model.addAttribute("messages", messages);
		return "index";
	}

	@GetMapping(value = "/usersTable")
	public String printTable(ModelMap model) {
		List<User> users = userService.listUsers();
		model.addAttribute("users", users);
		return "usersTable";
	}

	@PostMapping(value = "/addUser")
	public String addUserInTable(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String lastName,
			@RequestParam(required = false) String email,
			Model model) {

		userService.add(new User(name,lastName,email));
		List<User> users = userService.listUsers();
		model.addAttribute("users", users);

		return "usersTable";
	}

	@PostMapping(value = "/deleteUser")
	public String addUserInTable(
			@RequestParam(required = false) Long id,
			Model model) {

		userService.delete(id);
		List<User> users = userService.listUsers();
		model.addAttribute("users", users);

		return "usersTable";
	}
}