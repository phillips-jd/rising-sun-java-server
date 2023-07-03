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

public class AvailablePetDTO {

    private int petId;
    private String petName;
    private String species;
    private String breed;
    private int age;
    private String imagePath;

    // Default Constructor
    public AvailablePetDTO() {

    }

    // Complete Constructor
    public AvailablePetDTO(int petId, String petName, String species, String breed,
                           int age, String imagePath) {
        this.petId = petId;
        this.petName = petName;
        this.species = species;
        this.breed = breed;
        this.age = age;
        this.imagePath = imagePath;
    }

    // Getters and Setters

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }
    public void setPetName(String petName) {
        this.petName = petName;
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
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}