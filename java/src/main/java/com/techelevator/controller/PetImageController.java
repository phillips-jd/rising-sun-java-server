package com.techelevator.controller;

import com.techelevator.dao.PetDao;
import com.techelevator.dao.PetImageDao;
import com.techelevator.model.PetImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
public class PetImageController {

    @Autowired
    private PetImageDao petImageDao;
    private PetDao petDao;

    public PetImageController(PetImageDao petImageDao, PetDao petDao) {
        this.petImageDao = petImageDao;
        this.petDao = petDao;
    }

    @RequestMapping(value = "/pets/images/{id}", method = RequestMethod.GET)
    List<PetImage> getAllImagesByPetId(@PathVariable int id) {
        return petImageDao.getAllImagesByPetId(id);
    }



}
