package com.techelevator.controller;

import com.techelevator.dao.AdoptionDao;
import com.techelevator.model.Adoption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

public class AdoptionController {

    @Autowired
    private AdoptionDao adoptionDao;

    public AdoptionController(AdoptionDao adoptionDao) {
        this.adoptionDao = adoptionDao;
    }

    @RequestMapping(path = "/adoptions", method = RequestMethod.GET)
    public List<Adoption> getAll() {
        return adoptionDao.findAll();
    }

    @RequestMapping(path = "/adoptions/id/{adoptionId}", method = RequestMethod.GET)
    public Adoption getAdoptionById(@PathVariable int adoptionId) {
        Adoption adoption = adoptionDao.getAdoptionById(adoptionId);
        return adoption;
    }

}
