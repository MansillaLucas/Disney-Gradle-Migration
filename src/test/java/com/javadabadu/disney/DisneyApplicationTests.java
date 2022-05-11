package com.javadabadu.disney;

import com.javadabadu.disney.models.entity.Usuario;
import com.javadabadu.disney.repository.RolRepository;
import com.javadabadu.disney.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DisneyApplicationTests {

	@Autowired
	UsuarioRepository userRepo;
	@Autowired
	RolRepository rolRepo;
	@Autowired
	private BCryptPasswordEncoder encorder;
	@Test
	void contextLoads() {
	}

	@Test
	public void crearUsu() {
		Usuario us= new Usuario();
		us.setUsername("u3");
		us.setPassword(encorder.encode("123"));
		us.setRol(rolRepo.findById(2).get());
		Usuario retorno = userRepo.save(us);
		assertTrue(retorno.getPassword().equals(us.getPassword()));
	}
}
