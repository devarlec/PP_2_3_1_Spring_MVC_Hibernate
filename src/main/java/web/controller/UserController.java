package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.User;
import web.service.UserService;
import web.service.UserValidationService;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

	private final UserService userService;
	private final UserValidationService userValidationService;

	@Autowired
	public UserController(UserService userService, UserValidationService userValidationService) {
		this.userService = userService;
		this.userValidationService = userValidationService;
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
			String name,
			String lastName,
			String email,
			Model model) {

		String msg;

		if (validateUser(new User(name,lastName,email), userValidationService)) {
			msg = "Fields must be filled!";
		} else {
			userService.add(new User(name, lastName, email));
			msg = "User successfully added!";
		}

		List<User> users = userService.listUsers();
		model.addAttribute("msg", msg);
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


	private static boolean validateUser(User user,
									   UserValidationService userValidationService) {
		// список отловленных нарушений правил, здесь просто возвращает пустой или нет
		Set<ConstraintViolation<User>> violations = userValidationService.validateUser(user);

		return !violations.isEmpty();
	}

}