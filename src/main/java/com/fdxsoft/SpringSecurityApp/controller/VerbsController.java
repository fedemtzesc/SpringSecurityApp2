package com.fdxsoft.SpringSecurityApp.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class VerbsController {

	@GetMapping("/get")
	public String helloGET() {
		return "Hello World - GET";
	}
	
	@PostMapping("/post")
	public String helloPOST() {
		return "Hello World - POST";
	}
	
	@PutMapping("/put")
	public String helloPUT() {
		return "Hello World - PUT";
	}
	
	@DeleteMapping("/delete")
	public String helloDELETE() {
		return "Hello World - DELETE";
	}
	
	@PatchMapping("/patch")
	public String helloPATCH() {
		return "Hello World - PATCH";
	}
	
}
