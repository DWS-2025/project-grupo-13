package com.grupo13.grupo13;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

@SpringBootApplication
@EnableSpringDataWebSupport( pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class Grupo13Application {

	//starts the app
	public static void main(String[] args) {
		SpringApplication.run(Grupo13Application.class, args);
	}

}
