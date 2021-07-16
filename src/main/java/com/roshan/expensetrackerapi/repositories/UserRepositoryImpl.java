package com.roshan.expensetrackerapi.repositories;

import com.roshan.expensetrackerapi.domain.User;
import com.roshan.expensetrackerapi.exceptions.EtAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
	private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
	private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME," +
			" LAST_NAME, EMAIL, PASSWORD FROM ET_USERS WHERE USER_ID = ?";

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Integer create(String firstName, String lastName, String email, String password) throws EtAuthException {
		try {
			KeyHolder keyHolder = new GeneratedKeyHolder();

			jdbcTemplate.update(connection -> {
				// second arg is a flag to return generated keys
				// this key is used to populate keyholder
				PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, firstName);
				ps.setString(2, lastName);
				ps.setString(3, email);
				ps.setString(4, password);
				return ps;
			}, keyHolder);
			// as we can have composite primary keys, we get a map
			// get the value of "USER_ID" key from the map
			return (Integer) keyHolder.getKeys().get("USER_ID");
		} catch (Exception e) {
			throw new EtAuthException("Invalid details. Could not create account");
		}
	}

	@Override
	public User findByEmailAndPassword(String email, String password) throws EtAuthException {
		return null;
	}

	@Override
	public Integer getCountByEmail(String email) {
		return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL,
				new Object[] {email}, Integer.class);
	}

	@Override
	public User findById(Integer userId) {
		return jdbcTemplate.queryForObject(SQL_FIND_BY_ID,
				new Object[] {userId}, userRowMapper);
	}

	private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
		return new User(rs.getInt("USER_ID"),
				rs.getString("FIRST_NAME"),
				rs.getString("LAST_NAME"),
				rs.getString("EMAIL"),
				rs.getString("PASSWORD"));
	});
}