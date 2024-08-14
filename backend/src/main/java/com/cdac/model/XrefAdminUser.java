package com.cdac.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class XrefAdminUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long adminId;

	private Long userId;

	public XrefAdminUser(Long adminId, Long userId) {
		this.adminId = adminId;
		this.userId = userId;
	}

}
