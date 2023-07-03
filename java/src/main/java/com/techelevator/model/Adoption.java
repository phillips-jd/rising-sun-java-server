package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Adoption {

    /*
    Modeling class after the following db table:

    CREATE TABLE adoption (
	adoption_id SERIAL,
	owner_name varchar(50) NOT NULL,
	owner_address varchar(50) NOT NULL,
	owner_email varchar(50) NOT NULL,
	owner_phone_number varchar(50) NOT NULL,
	adoption_date date NOT NULL,
	CONSTRAINT PK_adoption PRIMARY KEY (adoption_id)
    */

    // Data members
    @JsonProperty("adoption_id")
    private int adoptionId;             // serially-assigned unique ID for a transaction
    @JsonProperty("owner_name")
    @NotNull
    private String ownerName;           // a single string for the owner's first and last name
    @JsonProperty("owner_address")
    @NotNull
    private String ownerAddress;        // a single string for the owner's mailing address
    @JsonProperty("owner_email")
    @NotNull
    private String ownerEmail;          // a single string for the owner's email address
    @JsonProperty("owner_phone_number")
    @NotNull
    private String ownerPhoneNumber;    // a string for the owner's phone number
    @JsonProperty("adoption_date")
    @NotNull
    private LocalDate adoptionDate;          // the date of the adoption

    // Constructors
    // default constructor
    public Adoption(){
    }

    // adoption constructor using every attribute but the serially-assigned adoptionId
    public Adoption(String ownerName, String ownerAddress, String ownerEmail, String ownerPhoneNumber, LocalDate adoptionDate) {
        this.ownerName = ownerName;
        this.ownerAddress = ownerAddress;
        this.ownerEmail = ownerEmail;
        this.ownerPhoneNumber = ownerPhoneNumber;
        this.adoptionDate = adoptionDate;
    }

    // Getters and Setters
    public int getAdoptionId() {
        return adoptionId;
    }
    public void setAdoptionId(int adoptionId) { this.adoptionId = adoptionId; }
    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
    public String getOwnerAddress() {
        return ownerAddress;
    }
    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }
    public String getOwnerEmail() {
        return ownerEmail;
    }
    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }
    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }
    public @NotNull LocalDate getAdoptionDate() {
        return adoptionDate;
    }
    public void setAdoptionDate(@NotNull LocalDate adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    @Override
    public String toString() {
        return "Adoption{" +
                "adoptionId=" + adoptionId +
                ", ownerName='" + ownerName + '\'' +
                ", ownerAddress='" + ownerAddress + '\'' +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", ownerPhoneNumber='" + ownerPhoneNumber + '\'' +
                ", adoptionDate=" + adoptionDate +
                '}';
    }

}
