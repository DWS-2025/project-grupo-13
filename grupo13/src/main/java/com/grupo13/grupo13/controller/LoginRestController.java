package com.grupo13.grupo13.controller;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.grupo13.grupo13.security.jwt.AuthResponse;
import com.grupo13.grupo13.security.jwt.LoginRequest;
import com.grupo13.grupo13.security.jwt.UserLoginService;
import com.grupo13.grupo13.security.jwt.AuthResponse.Status;
import com.grupo13.grupo13.service.UserService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class LoginRestController {
	
	@Autowired
	private UserLoginService userService;

	@Autowired
	private UserService ActualUserService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(
			@RequestBody LoginRequest loginRequest,
			HttpServletResponse response) {
		
		return userService.login(response, loginRequest);
	}

	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(
			@CookieValue(name = "RefreshToken", required = false) String refreshToken, HttpServletResponse response) {

		return userService.refresh(response, refreshToken);
	}

	@PostMapping("/logout")
	public ResponseEntity<AuthResponse> logOut(HttpServletResponse response) {
        return ResponseEntity.ok(new AuthResponse(Status.SUCCESS, userService.logout(response)));
	}

	//register
	@PostMapping("/register")	
	public ResponseEntity<String> register(@RequestBody Map<String, String> body) {
        String username = body.get("username");
    	String password = body.get("password");
		if(username.equals("") || password.equals("")){
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuario o contraseña invalidos");
		}else if (!ActualUserService.userExists(username)) {
            ActualUserService.createUser(username, password);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado con éxito");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El usuario ya existe");
        }
    }
} 