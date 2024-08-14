package com.cdac.model;

import java.sql.Date;

import com.cdac.dto.RequestCertificateDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
public class Certificate {

	public Certificate() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String issuer;
	private Date date;
	private String certificateName;
	private Long userId;
	private boolean isAuthorized;

	@Lob
	private byte[] pdfContent;

	public Certificate(RequestCertificateDto dto, Long userId) {
		this.name = dto.getName();
		this.issuer = dto.getIssuer();
		long millis = System.currentTimeMillis();
		this.date = new Date(millis);
		this.certificateName = dto.getCertificateName();
		this.userId = userId;
	}

}
