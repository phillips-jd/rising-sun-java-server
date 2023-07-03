// "DAO is an abstraction for accessing data, the idea is to separate the technical
// details of data access from the rest of the application. It can apply to any kind
// of data.
//
// "JDBC is an API for accessing relational databases using Java.
//
// "JDBC is more low-level than an ORM, it maps some Java types to SQL types but no
// more than that, it just takes DDL and DML, executes it, and returns result sets."
// -- Nathan Hughes, https://stackoverflow.com/questions/7070467/dao-and-jdbc-relation

package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.Adoption;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAdoptionDao implements AdoptionDao {

    // Data members
    private final JdbcTemplate jdbcTemplate;

    public JdbcAdoptionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Adoption getAdoptionById(int adoptionId) {
        String sql = "SELECT * FROM adoption WHERE adoption_id = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, adoptionId);
        if (results.next()) {
            return mapRowToAdoption(results);
        } else {
            throw new DaoException("Adoption was not found.");
        }
    }

    @Override
    public List<Adoption> findAll() {
        List<Adoption> adoptions = new ArrayList<>();
        String sql = "select * from adoption";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            Adoption adoption = mapRowToAdoption(results);
            adoptions.add(adoption);
        }
        return adoptions;
    }


    @Override
    public int create(String ownerName, String ownerAddress, String ownerEmail,
                          String ownerPhoneNumber, LocalDate adoptionDate) {
        String insertUserSql = "insert into adoption (owner_name, owner_address, owner_email, owner_phone_number, adoption_date) " +
                "values (?,?,?,?,?) " +
                "RETURNING adoption_id;";
        try {
            return jdbcTemplate.queryForObject(insertUserSql, int.class, ownerName, ownerAddress, ownerEmail,
                    ownerPhoneNumber, adoptionDate);
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new DaoException("Bad SQL grammar - Review the SQL statement syntax");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("Invalid operation - Data integrity error");
        }
    }

    @Override
    public int update(String ownerName, String ownerAddress, String ownerEmail,
                      String ownerPhoneNumber, LocalDate adoptionDate) {
        String sql = "UPDATE adoption " +
                "SET owner_name = ?, owner_address = ?, owner_email = ?, owner_phone_number = ?, adoption_date = ? " +
                "WHERE adoption_id = ?;";
        try {
            int rowsUpdated = jdbcTemplate.update(sql, ownerName, ownerAddress,
                    ownerEmail, ownerPhoneNumber, adoptionDate);
            return rowsUpdated;
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new DaoException("Bad SQL grammar - Review the SQL statement syntax");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("Invalid operation - Data integrity error");
        }
    }


    private Adoption mapRowToAdoption(SqlRowSet rs) {
        Adoption adoption = new Adoption();
        adoption.setAdoptionId(rs.getInt("adoption_id"));
        adoption.setOwnerName(rs.getString("owner_name"));
        adoption.setOwnerAddress(rs.getString("owner_address"));
        adoption.setOwnerEmail(rs.getString("owner_email"));
        adoption.setOwnerPhoneNumber(rs.getString("owner_phone_number"));
        adoption.setAdoptionDate(rs.getDate("adoption_date").toLocalDate());
        return adoption;
    }

}
