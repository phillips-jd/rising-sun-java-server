package com.techelevator.controller;

import com.techelevator.dao.*;
import com.techelevator.dao.AdoptionDao;
import com.techelevator.exception.DaoException;
import com.techelevator.model.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class PetController {

    @Autowired
    private PetDao petDao;
    private PetImageDao petImageDao;
    private AdoptionDao adoptionDao;
    private PetAdoptionDao petAdoptionDao;

    public PetController(PetDao petDao, PetImageDao petImageDao,
                         AdoptionDao adoptionDao, PetAdoptionDao petAdoptionDao) {
        this.petDao = petDao;
        this.petImageDao = petImageDao;
        this.adoptionDao = adoptionDao;
        this.petAdoptionDao = petAdoptionDao;
    }

    @RequestMapping(path = "/pets/id/{petId}", method = RequestMethod.GET)
    public Pet getPetById(@PathVariable int petId) {
        Pet pet = petDao.getPetById(petId);
        return pet;
    }

    @RequestMapping(path = "/pets", method = RequestMethod.GET)
    public List<AvailablePetDTO> getAvailablePetDTOs() {
        return petDao.getAvailablePetDTOs();
    }

    @RequestMapping(path = "/celebrate", method = RequestMethod.GET)
    public List<AdoptedPetDTO> getAdoptedPets() {
        return petDao.getAdoptedPets();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/update", method = RequestMethod.POST)
    public void addPet(@RequestBody AddPetDTO addPetDTO) {
        try {
            int newPetId = petDao.create(addPetDTO.getPetName(), addPetDTO.getAge(), addPetDTO.getSpecies(), addPetDTO.getBreed(),
                    addPetDTO.getWeight(), addPetDTO.isRedFlag(), addPetDTO.getGender(), addPetDTO.isAdoptedStatus(),
                    addPetDTO.getDescription());
            if(!addPetDTO.getPetImageDTOOne().getImagePath().isEmpty()) {
                petImageDao.addImage(newPetId, addPetDTO.getPetImageDTOOne().getImageName(), addPetDTO.getPetImageDTOOne().getImagePath());
            }
            if(!addPetDTO.getPetImageDTOTwo().getImagePath().isEmpty()) {
                petImageDao.addImage(newPetId, addPetDTO.getPetImageDTOTwo().getImageName(), addPetDTO.getPetImageDTOTwo().getImagePath());
            }
            if(!addPetDTO.getPetImageDTOThree().getImagePath().isEmpty()) {
                petImageDao.addImage(newPetId, addPetDTO.getPetImageDTOThree().getImageName(), addPetDTO.getPetImageDTOThree().getImagePath());
            }
        } catch (Exception e) {
            throw new DaoException("Pet Table Insertion Error.");
        }
    }

    // This is going to be a bit complicated. Need to do the following:
    // 1. update the pet entry. need to update based on pet_id, but how are we retrieving that?
        // a. consider updating based on pet_name - issue becomes, what to do when
        //    there are multiple pets with the same name
        // b. we could have users update pet listings based on a drop-down menu that pulls from available pets,
        //    but the issue of pets with the same name remains
    // 2. create an entry in the adoption table if the adoption status is updated
    // 3. create an entry in the pet_adoption table if the pet is adopted
    // 4. update the pet_image table if new images are added
    // All of this data needs to be pulled from an UpdatePetDTO
    // Plan to use a form similar to AddPet to collect most information
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/update", method = RequestMethod.PUT)
    public void updatePet(@RequestBody UpdatePetDTO updatePetDTO) {
        try {
            petDao.update(updatePetDTO.getPetName(), updatePetDTO.getAge(), updatePetDTO.getSpecies(), updatePetDTO.getBreed(),
                    updatePetDTO.getWeight(), updatePetDTO.isRedFlag(), updatePetDTO.getGender(), updatePetDTO.isAdoptedStatus(),
                    updatePetDTO.getDescription(), updatePetDTO.getPetId());
            if(!updatePetDTO.getPetImageDTOOne().getImagePath().isEmpty()) {
                petImageDao.addImage(updatePetDTO.getPetId(), updatePetDTO.getPetImageDTOOne().getImageName(), updatePetDTO.getPetImageDTOOne().getImagePath());
            }
            if(!updatePetDTO.getPetImageDTOTwo().getImagePath().isEmpty()) {
                petImageDao.addImage(updatePetDTO.getPetId(), updatePetDTO.getPetImageDTOTwo().getImageName(), updatePetDTO.getPetImageDTOTwo().getImagePath());
            }
            if(!updatePetDTO.getPetImageDTOThree().getImagePath().isEmpty()) {
                petImageDao.addImage(updatePetDTO.getPetId(), updatePetDTO.getPetImageDTOThree().getImageName(), updatePetDTO.getPetImageDTOThree().getImagePath());
            }
            if(updatePetDTO.isAdoptedStatus()) {
                int adoptionId = adoptionDao.create(updatePetDTO.getOwnerName(), updatePetDTO.getOwnerAddress(), updatePetDTO.getOwnerEmail(),
                        updatePetDTO.getOwnerPhoneNumber(), updatePetDTO.getAdoptionDate());
                petAdoptionDao.create(updatePetDTO.getPetId(), adoptionId);
            }
        } catch (Exception e) {
            throw new DaoException("Pet Table Insertion Error.");
        }
    }

    @RequestMapping(path = "/update", method = RequestMethod.GET)
    public List<Pet> getAvailablePets() {
        return petDao.getAvailablePets();
    }

}
