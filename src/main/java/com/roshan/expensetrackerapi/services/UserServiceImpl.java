package com.roshan.expensetrackerapi.services;

import com.roshan.expensetrackerapi.domain.User;
import com.roshan.expensetrackerapi.exceptions.EtAuthException;
import com.roshan.expensetrackerapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	@Override
	public User validateUser(String email, String password) throws EtAuthException {
		return null;
	}

	@Override
	public User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException {
		// Simple pattern for matching email format
		Pattern pattern = Pattern.compile("^(.+)@(.+)$");
		// convert email to lowercase; email is always case insensitive
		if (email != null) email = email.toLowerCase();
		// test against the email pattern
		if (!pattern.matcher(email).matches())
			throw new EtAuthException("Invalid email format");

		// format is correct; check if email is in use
		Integer count = userRepository.getCountByEmail(email);
		if (count > 0)
			throw new EtAuthException("Email already in use");
		// create the user
		Integer userId = userRepository.create(firstName, lastName, email, password);
		return userRepository.findById(userId);

		// we're performing multiple DB operations (using the repository methods)
		// in one single service method; hence, we need these to be encapsulated in a transaction
	}
}
