package com.cdac.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cdac.dto.ForgotPasswordDto;
import com.cdac.dto.RequestCertificateDto;
import com.cdac.model.Certificate;
import com.cdac.repository.CertificateRepository;
import com.cdac.util.GenerateCertificate;
import com.cdac.util.Utility;

@Service
public class CertificateService {

	@Autowired
	CertificateRepository certificateRepository;

	@Autowired
	GenerateCertificate generateCertificate;

	@Autowired
	Utility utility;

	public ResponseEntity<InputStreamResource> saveCertificate(RequestCertificateDto dto) {

		try {
			Certificate certificate = new Certificate(dto, utility.getUserId());
			Certificate cert = certificateRepository.save(certificate); //Record in DB
			return generateCertificate.publish(cert); // Generating via template
		} catch (Exception e) {
			throw e;
		}

	}

	public ResponseEntity<?> checkIsGenerated() {
		try {
			List<Certificate> certificate = certificateRepository.findByUserId(utility.getUserId());
			return ResponseEntity.status(HttpStatus.OK)
					.body(certificate != null && certificate.size() > 0 ? true : false);
		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseEntity<?> getAllCertificates() {
		try {
			checkIsGenerated();
			List<Certificate> certificate = certificateRepository.findByUserId(utility.getUserId());
			Function<Certificate, Certificate> func = t->{
			t.setPdfContent(t.isAuthorized()?t.getPdfContent():null);
			return t;
			};
			certificate.stream().map(func).collect(Collectors.toList());
			return ResponseEntity.status(HttpStatus.OK).body(certificate.stream().map(func).collect(Collectors.toList()));

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Certificate> getAllCertificatesAdmin() {
		return certificateRepository.getAllCertificatesAdmin();
	}

	public String authorizeCertificate(Long id) {
		try {
			Certificate certificate = certificateRepository.findById(id).get();
			certificate.setAuthorized(true);
			certificateRepository.save(certificate);
			return "Ok";
		} catch (Exception e) {
			throw e;
		}
	}
	public String deauthorizeCertificate(Long id) {
		try {
			Certificate certificate = certificateRepository.findById(id).get();
			certificate.setAuthorized(false);
			certificateRepository.save(certificate);
			return "Ok";
		} catch (Exception e) {
			throw e;
		}
	}
}
