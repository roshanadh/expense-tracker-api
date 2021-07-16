package com.roshan.expensetrackerapi.services;

import com.roshan.expensetrackerapi.domain.User;
import com.roshan.expensetrackerapi.exceptions.EtAuthException;

public interface UserService {

	User validateUser(String email, String password) throws EtAuthException;
	User registerUser(String firstName, String lastName, String email, String password) throws EtAuthException;
}
