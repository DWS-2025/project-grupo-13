package com.grupo13.grupo13.controller;
import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.grupo13.grupo13.security.jwt.UserLoginService;
import com.grupo13.grupo13.service.UserService;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LoginWebController {

    private final UserLoginService userLoginService;
	
	@Autowired
	UserService userService;

    LoginWebController(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();
        if (principal != null) {
			return "/";
		}
		return "login";
	}

	@GetMapping("/loginerror")
	public String loginerror() {
		return "loginerror";
	}

	@GetMapping("/register")
	public String showRegster() {
		if((userService.getLoggedUserOrNull()) == null || userService.getLoggedUserDTOOrNull().roles().contains("ADMIN")){
			return "register";
		}else{
			return "redirect:/character";
		}
	}

	@PostMapping("/register")
	public String regster(Model model, @RequestParam String username, @RequestParam String password) {
		if(!userService.userExists(username)){
			userService.createUser(username, password);
			return "redirect:/login";
		}else{
			model.addAttribute("message", "Name already exists");
            return "sp_errors";
		}
	}
}

