package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import java.util.List;

public class AddPetDTO {

    private String petName;
    private int age;
    private String species;
    private String breed;
    private int weight;
    private String gender;
    private boolean redFlag;
    private boolean adoptedStatus;
    private String description = "";
    private PetImageDTO petImageDTOOne;
    private PetImageDTO petImageDTOTwo;
    private PetImageDTO petImageDTOThree;

    public AddPetDTO() {

    }

    public AddPetDTO(String petName, int age, String species,
                     String breed, int weight, String gender, boolean redFlag,
                     boolean adoptedStatus, String description,
                     PetImageDTO petImageDTOOne, PetImageDTO petImageDTOTwo, PetImageDTO petImageDTOThree) {
        this.petName = petName;
        this.age = age;
        this.species = species;
        this.breed =breed;
        this.weight = weight;
        this.gender = gender;
        this.redFlag = redFlag;
        this.adoptedStatus = adoptedStatus;
        this.description = description;
        this.petImageDTOOne = petImageDTOOne;
        this.petImageDTOTwo = petImageDTOTwo;
        this.petImageDTOThree = petImageDTOThree;
    }


    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isRedFlag() {
        return redFlag;
    }

    public void setRedFlag(boolean redFlag) {
        this.redFlag = redFlag;
    }

    public boolean isAdoptedStatus() {
        return adoptedStatus;
    }

    public void setAdoptedStatus(boolean adoptedStatus) {
        this.adoptedStatus = adoptedStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PetImageDTO getPetImageDTOOne() {
        return petImageDTOOne;
    }

    public void setPetImageDTOOne(PetImageDTO petImageDTOOne) {
        this.petImageDTOOne = petImageDTOOne;
    }

    public PetImageDTO getPetImageDTOTwo() {
        return petImageDTOTwo;
    }

    public void setPetImageDTOTwo(PetImageDTO petImageDTOTwo) {
        this.petImageDTOTwo = petImageDTOTwo;
    }

    public PetImageDTO getPetImageDTOThree() {
        return petImageDTOThree;
    }

    public void setPetImageDTOThree(PetImageDTO petImageDTOThree) {
        this.petImageDTOThree = petImageDTOThree;
    }
}
