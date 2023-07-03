package com.techelevator.dao;

import com.techelevator.exception.DaoException;
import com.techelevator.model.AdoptedPetDTO;
import com.techelevator.model.PetImage;
import com.techelevator.model.PetImageDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPetImageDao implements PetImageDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcPetImageDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<PetImage> getAllImagesByPetId(int id){
        List<PetImage> petImageList = new ArrayList<>();
        String sql = "SELECT pi.pet_id, pi.image_name, pi.image_path " +
                "FROM pet_image AS pi " +
                "JOIN pet AS p ON p.pet_id = pi.pet_id " +
                "WHERE pi.pet_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
            while(results.next()) {
                PetImage petImage = mapRowToPetImage(results);
                petImageList.add(petImage);
            }
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new DaoException("Bad SQL grammar - Review the SQL statement syntax");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("Invalid operation - Data integrity error");
        }
        return petImageList;
    }

    @Override
    public void addImage(int petId, String imageName, String imagePath) {
        String sql = "INSERT INTO pet_image (pet_id, image_name, image_path) " +
                "VALUES (?, ?, ?);";
        try {
            jdbcTemplate.update(sql, petId, imageName, imagePath);
        } catch(CannotGetJdbcConnectionException e) {
            throw new DaoException("Could not connect to data source");
        } catch(BadSqlGrammarException e) {
            throw new DaoException("Bad SQL grammar - Review the SQL statement syntax");
        } catch(DataIntegrityViolationException e) {
            throw new DaoException("Invalid operation - Data integrity error");
        }
    }

    private PetImage mapRowToPetImage(SqlRowSet rs) {
        PetImage petImage = new PetImage();
        petImage.setPetId(rs.getInt("pet_id"));
        petImage.setImageName(rs.getString("image_name"));
        petImage.setImagePath(rs.getString("image_path"));
        return petImage;
    }

    private PetImageDTO mapRowToPetImageDTO(SqlRowSet rs) {
        PetImageDTO petImageDTO = new PetImageDTO();
        petImageDTO.setImageName(rs.getString("image_name"));
        petImageDTO.setImagePath(rs.getString("image_path"));
        return petImageDTO;
    }

}
