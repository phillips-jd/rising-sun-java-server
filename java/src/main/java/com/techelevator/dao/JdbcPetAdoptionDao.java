package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Adoption;
import com.techelevator.model.PetAdoption;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPetAdoptionDao implements PetAdoptionDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPetAdoptionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<PetAdoption> findAll() {
        List<PetAdoption> petAdoptions = new ArrayList<>();
        String sql = "select * from pet_adoption";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            PetAdoption petAdoption = mapRowToPetAdoption(results);
            petAdoptions.add(petAdoption);
        }
        return petAdoptions;
    }

    @Override
    public PetAdoption getPetAdoptionById(int adoptionId) {
        String sql = "SELECT * FROM pet_adoption WHERE adoption_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, adoptionId);
        if (results.next()) {
            return mapRowToPetAdoption(results);
        } else {
            throw new DaoException("Adoption was not found.");
        }
    }

    @Override
    public void create(int petId, int adoptionId) {
        String sql = "INSERT INTO pet_adoption (pet_id, adoption_id) " +
                "VALUES(?, ?);";
        try {
            jdbcTemplate.update(sql, petId, adoptionId);
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new DaoException("Bad SQL grammar - Review the SQL statement syntax");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("Invalid operation - Data integrity error");
        }
    }

    @Override
    public int delete(int petId) {
        String sql = "DELETE FROM pet_adoption " +
                "WHERE pet_id = ?;";
        try {
            return jdbcTemplate.update(sql, int.class, petId);
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new DaoException("Bad SQL grammar - Review the SQL statement syntax");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("Invalid operation - Data integrity error");
        }
    }

    public PetAdoption mapRowToPetAdoption(SqlRowSet rs) {
        PetAdoption petAdoption = new PetAdoption();
        petAdoption.setPetId(rs.getInt("pet_id"));
        petAdoption.setAdoptionId(rs.getInt("adoption_id"));
        return petAdoption;
    }

}
