package com.cdac.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.cdac.repository.UserRepository;

@Component
public class Utility {
	
	@Autowired
	UserRepository userRepository;
	
	public Long getUserId() {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) authentication.getPrincipal();
				return userRepository.findByUsername(userDetails.getUsername()).getId();
			}
			return null;
		}

	}

