package com.cdac.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cdac.dto.RequestCertificateDto;
import com.cdac.service.CertificateService;

@RestController
@RequestMapping("/user/certificate")
public class CertificateController {

	@Autowired
	private CertificateService certificateService;

	@PostMapping("/request")
	public ResponseEntity<?> uploadCertificate(@RequestBody RequestCertificateDto dto) {

		try {
			certificateService.saveCertificate(dto);
			return ResponseEntity.status(HttpStatus.OK).body("Certificate Generated");
		} catch (DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Duplicate entry");

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while generating the Certificate");
		}

	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllCertificates() {
		try {
			return certificateService.getAllCertificates();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking the Certificate");
		}

	}

	@GetMapping("/is-generated")
	public ResponseEntity<?> isGeneratedCertificate() {

		try {
			return certificateService.checkIsGenerated();

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while checking the Certificate");
		}

	}

}
