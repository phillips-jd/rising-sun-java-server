// "DAO is an abstraction for accessing data, the idea is to separate the technical
// details of data access from the rest of the application. It can apply to any kind
// of data.
//
// "JDBC is an API for accessing relational databases using Java.
//
// "JDBC is more low-level than an ORM, it maps some Java types to SQL types but no
// more than that, it just takes DDL and DML, executes it, and returns result sets."
// -- Nathan Hughes, https://stackoverflow.com/questions/7070467/dao-and-jdbc-relation

package com.techelevator.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.UserNotFoundException;
import com.techelevator.model.LoginDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.techelevator.model.User;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;
    private String tempPassword;
    private String username;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        if (username == null) throw new IllegalArgumentException("Username cannot be null");

        int userId;
        try {
            userId = jdbcTemplate.queryForObject("select user_id from users where username = ?", int.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new UsernameNotFoundException("User " + username + " was not found.");
        }

        return userId;
    }

	@Override
	public User getUserById(int userId) {
		String sql = "SELECT * FROM users WHERE user_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
		if (results.next()) {
			return mapRowToUser(results);
		} else {
			throw new UserNotFoundException();
		}
	}

    @Override
    public User getUserEmailById(int userId) {
        String sql = "SELECT email_address FROM users WHERE user_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            return mapRowToUser(results);
        } else {
            throw new UserNotFoundException();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "select * from users";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }

        return users;
    }

    @Override
    public User findByUsername(String username) {
        for (User user : this.findAll()) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                return user;
            }
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public String create(String username, String role, String firstName, String lastName,
                          String emailAddress, String phoneNumber, int age, String emergencyFirstName, String emergencyLastName,
                          String emergencyPhone) {
        String insertUserSql = "insert into users (username,password_hash,role,first_name,last_name,email_address,phone_number,age,emerg_first_name,emerg_last_name,emerg_phone)" +
                               "values (?,?,?,?,?,?,?,?,?,?,?)";
        Random randomNumberGenerator = new Random();
        int min = 10000;
        int max = 99999;
        int randomNumber = randomNumberGenerator.nextInt(max - min + 1) + min;
        String password = "temp" + randomNumber;
        String password_hash = new BCryptPasswordEncoder().encode(password);
        String ssRole = role.toUpperCase().startsWith("ROLE_") ? role.toUpperCase() : "ROLE_" + role.toUpperCase();
        jdbcTemplate.update(insertUserSql, username, password_hash, ssRole, firstName, lastName,
                                  emailAddress, phoneNumber, age, emergencyFirstName, emergencyLastName, emergencyPhone);
        return password;
    }

    @Override
    public void setTempPassword(String tempPassword) {
        this.tempPassword = tempPassword;
    }
    @Override
    public String getTempPassword() {
        return this.tempPassword;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean changePassword(String username, String password) {
        String insertUserSql = "UPDATE users SET password_hash = ?, first_login = false WHERE username = ?;";
        try {
            String password_hash = new BCryptPasswordEncoder().encode(password);
            return jdbcTemplate.update(insertUserSql, password_hash, username) == 1;
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new DaoException("Bad SQL grammar - Review the SQL statement syntax");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("Invalid operation - Data integrity error");
        }
    }

    @Override
    public List<User> viewPendingApplications() {
        List<User> pendingUsers = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash, role, first_name, last_name, email_address, phone_number, " +
                "age, emerg_first_name, emerg_last_name, emerg_phone, first_login FROM users WHERE " +
                "role = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, "ROLE_PENDING");
        while (results.next()) {
            User user = mapRowToUser(results);
            pendingUsers.add(user);
        }
        return pendingUsers;
    }

    @Override
    public boolean approveVolunteer(User user, int userId) {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        try {
            return jdbcTemplate.update(sql, "ROLE_USER", userId) == 1;
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new DaoException("Bad SQL grammar - Review the SQL statement syntax");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("Invalid operation - Data integrity error");
        }
    }

    @Override
    public boolean declineVolunteer(User user, int userId) {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        try {
            return jdbcTemplate.update(sql, "ROLE_DECLINED", userId) == 1;
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new DaoException("Bad SQL grammar - Review the SQL statement syntax");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("Invalid operation - Data integrity error");
        }

    }

    @Override
    public boolean promoteToAdmin(User user, int userId) {
        String sql = "UPDATE users SET role = ? WHERE user_id = ?";
        try {
            return jdbcTemplate.update(sql, "ROLE_ADMIN", userId) == 1;
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new DaoException("Bad SQL grammar - Review the SQL statement syntax");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("Invalid operation - Data integrity error");
        }
    }

    @Override
    public String getRole(String username) {
        String sql = "SELECT role FROM users WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        if (results.next()) {
            return results.getString("role");
        } else {
            return null;
        }
    }


    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setAuthorities(Objects.requireNonNull(rs.getString("role")));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmailAddress(rs.getString("email_address"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setAge(rs.getInt("age"));
        user.setEmergencyFirstName(rs.getString("emerg_first_name"));
        user.setEmergencyLastName(rs.getString("emerg_last_name"));
        user.setEmergencyPhone(rs.getString("emerg_phone"));
        user.setFirstLogin(rs.getBoolean("first_login"));
        user.setActivated(true);
        return user;
    }
}
