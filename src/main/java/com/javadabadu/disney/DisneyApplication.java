package com.javadabadu.disney;

import com.javadabadu.disney.models.entity.Rol;
import com.javadabadu.disney.models.entity.Usuario;
import com.javadabadu.disney.service.UsuarioService;
import com.javadabadu.disney.service.impl.UsuarioServiceImpl;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;

@SpringBootApplication
@EnableWebMvc
public class DisneyApplication {

	public static void main(String[] args) {
		SpringApplication.run(DisneyApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}



