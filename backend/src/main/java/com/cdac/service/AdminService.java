package com.cdac.service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cdac.dto.CreateUserRequestDto;
import com.cdac.dto.ForgotPasswordDto;
import com.cdac.exception.UserAlreadyExistException;
import com.cdac.model.User;
import com.cdac.model.XrefAdminUser;
import com.cdac.repository.UserRepository;
import com.cdac.repository.XrefAdminUserRepository;
import com.cdac.util.Utility;

@Service
public class AdminService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	Utility utility;

	@Autowired
	XrefAdminUserRepository xrefAdminUserRepository;

	public XrefAdminUser createUser(CreateUserRequestDto createUser) throws UserAlreadyExistException {

		validateUser(createUser);

		Long userId = utility.getUserId();

		User userObj = userRepository
				.save(new User(createUser.getUsername(), passwordEncoder.encode(createUser.getPassword()), "USER"));

		XrefAdminUser xrefAdminUser = new XrefAdminUser(userId, userObj.getId());

		return xrefAdminUserRepository.save(xrefAdminUser);
	}

	private void validateUser(CreateUserRequestDto createUser) throws UserAlreadyExistException {

		if (userRepository.findByUsername(createUser.getUsername()) != null) {

			throw new UserAlreadyExistException("User Already Exist");

		}

	}

	public List<User> getAllUsers() {

		List<Long> user = xrefAdminUserRepository.findByAdminId(utility.getUserId());
		List<User> listUser = userRepository.findByUserIds(user);

		Function<User, User> res = t -> {
			t.setPassword(null);
			return t;
		};

		return listUser.stream().map(res).collect(Collectors.toList());

	}

	public String forgotPassword(ForgotPasswordDto dto) {
		try {

			User user = userRepository.findById(dto.getId()).get();
			user.setPassword(passwordEncoder.encode(dto.getPassword()));
			userRepository.save(user);
			return "ok";
		} catch (Exception e) {
			throw e;
		}
	}

}
