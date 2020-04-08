package com.example.demo.dao;

import com.example.demo.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.IOError;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("postgrs-0")
public class PersonDataAccessService implements PersonDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int insertPerson(UUID id, Person person) {
        return 0;
    }

    @Override
    public List<Person> selectAllPeople() {
        final String sql = "SELECT id,name FROM person";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            return new Person(UUID.fromString(resultSet.getString("id")), resultSet.getString("name"));
        });

    }

    @Override
    public int deletePersonById(UUID id) {
        final String sql = "DELETE FROM person where id = ?";
        try {
            jdbcTemplate.update(sql, id);
            return 1;
        }catch (IOError error){
            System.out.println("Couldnt delete record");
            return 0;
        }
    }

    @Override
    public int updatePersonById(UUID id, Person person) {
        final String sql = "UPDATE Person set name = ? where id = ? ";
        try {
            jdbcTemplate.update(sql,person.getName(),id);
            System.out.println("Updated Record with ID = " + id );
            return 1;

        }catch (IOError error){
            System.out.println("Could not Updated Record with ID = " + id );
            return 0;
        }
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT id,name FROM person where id = ?";
        Person person = jdbcTemplate.queryForObject(sql, new Object[]{id},(resultSet, i) -> {
            return new Person(UUID.fromString(resultSet.getString("id")), resultSet.getString("name"));
        });
        return Optional.ofNullable(person);
    }
}
