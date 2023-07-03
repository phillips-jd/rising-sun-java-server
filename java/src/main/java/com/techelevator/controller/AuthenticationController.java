package com.techelevator.controller;

import javax.validation.Valid;

import com.techelevator.exception.DaoException;
import com.techelevator.exception.UserAlreadyExistsException;
import com.techelevator.model.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.techelevator.dao.UserDao;
import com.techelevator.security.jwt.JWTFilter;
import com.techelevator.security.jwt.TokenProvider;

import java.security.Principal;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDao userDao;

    public AuthenticationController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, UserDao userDao) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDao = userDao;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginDTO loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);
        
        User user = userDao.findByUsername(loginDto.getUsername());

        boolean firstLogin = user.isFirstLogin();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        return new ResponseEntity<>(new LoginResponse(jwt, user, firstLogin), httpHeaders, HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterUserDTO newUser) {
        try {
            User user = userDao.findByUsername(newUser.getUsername());
            throw new UserAlreadyExistsException();
        } catch (UsernameNotFoundException e) {
            String tempPassword = userDao.create(newUser.getUsername(), newUser.getRole(),
                          newUser.getFirstName(), newUser.getLastName(), newUser.getEmailAddress(), newUser.getPhoneNumber(), newUser.getAge(),
                          newUser.getEmergencyFirstName(), newUser.getEmergencyLastName(), newUser.getEmergencyPhone());
            userDao.setTempPassword(tempPassword);
            userDao.setUsername(newUser.getUsername());
        }
    }

//    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/change-password", method = RequestMethod.PUT)
    public void changePassword(@Valid @RequestBody LoginDTO loginDto) {
        try {
            User user = userDao.findByUsername(loginDto.getUsername());
            userDao.changePassword(user.getUsername(), loginDto.getPassword());
        } catch (UsernameNotFoundException e) {
            throw new DaoException("Could not find user.");
        }
    }

    @RequestMapping(value = "/role/{username}", method = RequestMethod.GET)
    public String getRole(@PathVariable String username) {
        try {
            return userDao.getRole(username);
        } catch (UsernameNotFoundException e) {
            throw new DaoException("Could not find user.");
        }
    }

    /**
     * Object to return as body in JWT Authentication.
     */
    static class LoginResponse {

        private String token;
        private User user;
        private boolean firstLogin;

        LoginResponse(String token, User user, boolean firstLogin) {
            this.token = token;
            this.user = user;
            this.firstLogin = firstLogin;
        }

        @JsonProperty("token")
        String getToken() {
            return token;
        }

        void setToken(String token) {
            this.token = token;
        }

        @JsonProperty("user")
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

        @JsonProperty("firstLogin")
        public boolean isFirstLogin() {
            return firstLogin;
        }

        public void setFirstLogin(boolean firstLogin) {
            this.firstLogin = firstLogin;
        }
    }
}

