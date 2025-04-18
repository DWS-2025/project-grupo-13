package com.grupo13.grupo13;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.grupo13.grupo13.model.User;

@SpringBootApplication
public class Grupo13Application {

	@Bean
	public User user(){
		return new User(100, "Lupe");
	}

	//starts the app
	public static void main(String[] args) {
		SpringApplication.run(Grupo13Application.class, args);
	}

}
