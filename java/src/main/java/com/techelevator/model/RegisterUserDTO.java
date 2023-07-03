// "DTOs or Data Transfer Objects are objects that carry data between processes in order to
// reduce the number of methods calls. The pattern was first introduced by Martin Fowler i
// n his book EAA.
//
// "Fowler explained that the pattern's main purpose is to reduce roundtrips to the server
//  by batching up multiple parameters in a single call. This reduces the network overhead
//  in such remote operations.
//
// "Another benefit is the encapsulation of the serialization's logic (the mechanism that
// translates the object structure and data to a specific format that can be stored and
// transferred). It provides a single point of change in the serialization nuances. It also
// decouples the domain models from the presentation layer, allowing both to change independently.
//
// "DTOs normally are created as POJOs. They are flat data structures that contain no business
// logic. They only contain storage, accessors and eventually methods related to serialization
// or parsing." -- https://www.baeldung.com/java-dto-pattern

package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class RegisterUserDTO {

    // Data members
    @NotEmpty
    private String username;
    @NotEmpty(message = "Please select a role for this user.")
    private String role;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String emailAddress;
    @NotEmpty
    private String phoneNumber;
    @Min(value = 0, message = "Age must be a positive number")
    private int age;
    @NotEmpty
    private String emergencyFirstName;
    @NotEmpty
    private String emergencyLastName;
    @NotEmpty
    private String emergencyPhone;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
