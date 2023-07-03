// "The Data Access Object (DAO) pattern is a structural pattern that allows us to isolate
// the application/business layer from the persistence layer (usually a relational database
// but could be any other persistence mechanism) using an abstract API.
//
// "The API hides from the application all the complexity of performing CRUD operations in
// the underlying storage mechanism. This permits both layers to evolve separately without
// knowing anything about each other.  -- https://www.baeldung.com/java-dao-pattern

package com.techelevator.dao;

import com.techelevator.model.Adoption;
import java.time.LocalDate;
import java.util.List;

public interface AdoptionDao {

    List<Adoption> findAll();
    Adoption getAdoptionById(int adoptionId);
    int create(String ownerName, String ownerAddress, String ownerEmail, String ownerPhoneNumber, LocalDate adoptionDate);
    int update(String ownerName, String ownerAddress, String ownerEmail, String ownerPhoneNumber, LocalDate adoptionDate);
}
