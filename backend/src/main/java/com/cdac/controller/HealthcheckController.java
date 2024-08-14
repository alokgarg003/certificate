package com.cdac.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthcheckController {
	
	@GetMapping("/healthcheck")
	public String healthcheck() {
		return "Ok";
	}

}
