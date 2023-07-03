package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PetAdoption {

    @JsonProperty("pet_id")
    private int petId;
    @JsonProperty("adoption_id")
    private int adoptionId;

    public PetAdoption() {

    }

    public PetAdoption(int petId, int adoptionId) {
        this.petId = petId;
        this.adoptionId = adoptionId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getAdoptionId() {
        return adoptionId;
    }

    public void setAdoptionId(int adoptionId) {
        this.adoptionId = adoptionId;
    }
}
