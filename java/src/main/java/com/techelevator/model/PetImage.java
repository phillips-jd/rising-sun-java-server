package com.techelevator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PetImage {

    @JsonProperty("pet_id")
    private int petId;
    @JsonProperty("image_name")
    private String imageName;
    @JsonProperty("image_path")
    private String imagePath;

    public PetImage() {

    }

    public PetImage(int petId, String imageName, String imagePath) {
        this.petId = petId;
        this.imageName = imageName;
        this.imagePath = imagePath;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
