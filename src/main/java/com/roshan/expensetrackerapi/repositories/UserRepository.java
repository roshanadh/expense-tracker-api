package com.roshan.expensetrackerapi.repositories;

import com.roshan.expensetrackerapi.domain.User;
import com.roshan.expensetrackerapi.exceptions.EtAuthException;

public interface UserRepository {

	Integer create(String firstName, String lastName, String email, String password) throws EtAuthException;

	User findByEmailAndPassword(String email, String password) throws EtAuthException;

	Integer getCountByEmail(String email);

	User findById(Integer userId);
}
