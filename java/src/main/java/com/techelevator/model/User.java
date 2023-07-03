package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
   @JsonProperty("user_id")
   private int id;
   @JsonProperty("username")
   private String username;
   @JsonIgnore
   private String password;
   @JsonIgnore
   private boolean activated;
   private Set<Authority> authorities = new HashSet<>();
   @JsonProperty("first_name")
   private String firstName;
   @JsonProperty("last_name")
   private String lastName;
   @JsonProperty("email_address")
   private String emailAddress;
   @JsonProperty("phone_number")
   private String phoneNumber;
   @JsonProperty("age")
   private int age;
   @JsonProperty("emerg_first_name")
   private String emergencyFirstName;
   @JsonProperty("emerg_last_name")
   private String emergencyLastName;
   @JsonProperty("emerg_phone")
   private String emergencyPhone;
   @JsonProperty("first_login")
   private boolean firstLogin = true;

   public User() { }

   public User(int id, String username, String password, String authorities, String firstName,
               String lastName, String emailAddress, String phoneNumber, int age,
               String emergencyFirstName, String emergencyLastName, String emergencyPhone) {
      this.id = id;
      this.username = username;
      this.password = password;
      if(authorities != null) this.setAuthorities(authorities);
      this.firstName = firstName;
      this.lastName = lastName;
      this.emailAddress = emailAddress;
      this.phoneNumber = phoneNumber;
      this.age = age;
      this.emergencyFirstName = emergencyFirstName;
      this.emergencyLastName = emergencyLastName;
      this.emergencyPhone = emergencyPhone;
      this.activated = true;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public boolean isActivated() {
      return activated;
   }

   public void setActivated(boolean activated) {
      this.activated = activated;
   }

   public Set<Authority> getAuthorities() {
      return authorities;
   }

   public void setAuthorities(Set<Authority> authorities) {
      this.authorities = authorities;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmailAddress() {
      return emailAddress;
   }

   public void setEmailAddress(String emailAddress) {
      this.emailAddress = emailAddress;
   }

   public int getAge() {
      return age;
   }

   public void setAge(int age) {
      this.age = age;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getEmergencyFirstName() {
      return emergencyFirstName;
   }

   public void setEmergencyFirstName(String emergencyFirstName) {
      this.emergencyFirstName = emergencyFirstName;
   }

   public String getEmergencyLastName() {
      return emergencyLastName;
   }

   public void setEmergencyLastName(String emergencyLastName) {
      this.emergencyLastName = emergencyLastName;
   }

   public String getEmergencyPhone() {
      return emergencyPhone;
   }

   public void setEmergencyPhone(String emergencyPhone) {
      this.emergencyPhone = emergencyPhone;
   }

   public boolean isFirstLogin() {
      return firstLogin;
   }

   public void setFirstLogin(boolean firstLogin) {
      this.firstLogin = firstLogin;
   }

   public void setAuthorities(String authorities) {
      String[] roles = authorities.split(",");
      for(String role : roles) {
         String authority = role.contains("ROLE_") ? role : "ROLE_" + role;
         this.authorities.add(new Authority(authority));
      }
   }



   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      User user = (User) o;
      return id == user.id &&
              activated == user.activated &&
              Objects.equals(username, user.username) &&
              Objects.equals(password, user.password) &&
              Objects.equals(authorities, user.authorities);
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, username, password, activated, authorities);
   }

   @Override
   public String toString() {
      return "User{" +
              "id=" + id +
              ", username='" + username + '\'' +
              ", activated=" + activated +
              ", authorities=" + authorities +
              '}';
   }
}
