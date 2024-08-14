package com.cdac.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.cdac.model.Certificate;
import com.cdac.repository.CertificateRepository;

import jakarta.persistence.EntityNotFoundException;

@Component
public class GenerateCertificate {

	private final TemplateEngine templateEngine;
	private final CertificateRepository certificateRepository;

	public GenerateCertificate(TemplateEngine templateEngine, CertificateRepository certificateRepository) {
		this.templateEngine = templateEngine;
		this.certificateRepository = certificateRepository;
	}

	public ResponseEntity<InputStreamResource> publish(Certificate certificate) {
		try {
			// Prepare the context for Thymeleaf template
			Context context = new Context();
			context.setVariable("name", certificate.getName());
			context.setVariable("issuer", certificate.getIssuer());
			context.setVariable("certificate_name", certificate.getCertificateName());
			context.setVariable("date", certificate.getDate());

			// Generate the HTML content using Thymeleaf
			String htmlContent = templateEngine.process("certificate.html", context);

			// Convert HTML to PDF
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			renderer.createPDF(byteArrayOutputStream);

			// Convert the PDF to a byte array
			byte[] pdfBytes = byteArrayOutputStream.toByteArray();

			// Save the PDF as a BLOB in the database
			savePdfToDatabase(certificate.getId(), pdfBytes);

			// Prepare the response
			ByteArrayInputStream inputStream = new ByteArrayInputStream(pdfBytes);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=certificate.pdf");

			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
					.body(new InputStreamResource(inputStream));

		} catch (Exception e) {
			throw new RuntimeException("Failed to generate PDF and save to database", e);
		}
	}

	private void savePdfToDatabase(Long certificateId, byte[] pdfBytes) {

		Certificate certificateEntity = certificateRepository.findById(certificateId)
				.orElseThrow(() -> new EntityNotFoundException("Certificate not found"));
		certificateEntity.setPdfContent(pdfBytes);
		certificateRepository.save(certificateEntity);
	}
}
