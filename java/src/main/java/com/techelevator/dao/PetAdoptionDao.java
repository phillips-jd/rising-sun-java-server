package com.techelevator.dao;

import com.techelevator.model.Adoption;
import com.techelevator.model.PetAdoption;

import java.time.LocalDate;
import java.util.List;

public interface PetAdoptionDao {

    List<PetAdoption> findAll();
    PetAdoption getPetAdoptionById(int adoptionId);
    void create(int petId, int adoptionId);
    int delete(int petId);

}
