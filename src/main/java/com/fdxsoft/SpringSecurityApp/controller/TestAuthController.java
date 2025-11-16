package com.fdxsoft.SpringSecurityApp.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class TestAuthController {

	@GetMapping("/hello") //Se tiene que cambiar de 'Basic Auth' a 'No Auth' en Postman para que funcione
	@PreAuthorize("permitAll()")
	public String hello() {
		return "Hello World!";
	}
	
	@GetMapping("/hello-secured")
	@PreAuthorize("hasAuthority('READ')")
	public String helloSecured() {
		return "Hello World Secured!";
	}
	
	@GetMapping("/hello-secured2")
	public String helloSecured2() {
		return "Hello World Undefined!";
	}
}
