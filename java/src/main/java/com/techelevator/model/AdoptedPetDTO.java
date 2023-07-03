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

import java.time.LocalDate;

public class AdoptedPetDTO {

    // Data members
    private String petName;
    private String ownerName;
    private LocalDate adoptionDate;
    private String imagePath;

    // Default Constructor
    public AdoptedPetDTO() {

    }

    public AdoptedPetDTO(String petName, String ownerName,
                         LocalDate adoptionDate, String imagePath) {
        this.petName = petName;
        this.ownerName = ownerName;
        this.adoptionDate = adoptionDate;
        this.imagePath = imagePath;
    }

    // Getters and Setters
    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public LocalDate getAdoptionDate() {
        return adoptionDate;
    }

    public void setAdoptionDate(LocalDate adoptionDate) {
        this.adoptionDate = adoptionDate;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
