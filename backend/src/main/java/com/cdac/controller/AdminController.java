package com.cdac.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.CreateUserRequestDto;
import com.cdac.dto.ForgotPasswordDto;
import com.cdac.exception.UserAlreadyExistException;
import com.cdac.model.Certificate;
import com.cdac.model.User;
import com.cdac.service.AdminService;
import com.cdac.service.CertificateService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	private CertificateService certificateService;

	@PostMapping("/create/user")
	public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDto createUser) {

		try {
			adminService.createUser(createUser);
			return new ResponseEntity<String>("User Created", HttpStatus.CREATED);
		} catch (UserAlreadyExistException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while creating the user");
		}
	}

	@GetMapping("/get/all/certificates")
	public ResponseEntity<?> getAllCertificates() {
		try {
			return new ResponseEntity<List<Certificate>>(certificateService.getAllCertificatesAdmin(), HttpStatus.OK);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while creating the user");
		}
	}
	
	@GetMapping("/get/all/users")
	public ResponseEntity<?> getAllUsers() {
		try {
			return new ResponseEntity<List<User>>(adminService.getAllUsers(), HttpStatus.OK);

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while creating the user");
		}
	}

	@PutMapping("/authorize")
	public ResponseEntity<?> authorizeCertificate(@RequestParam("id") Long id) {
		try {
			return new ResponseEntity<String>(certificateService.authorizeCertificate(id), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred authorizing");
		}

	}
	
	@PutMapping("/deauthorize")
	public ResponseEntity<?> deauthorizeCertificate(@RequestParam("id") Long id) {
		try {
			return new ResponseEntity<String>(certificateService.deauthorizeCertificate(id), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while deauthorizing");
		}

	}
	
	@PostMapping("/user/forgot")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDto dto) {
		try {
			return new ResponseEntity<String>(adminService.forgotPassword(dto), HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error forgetting password .");
		}

	}
	

}
