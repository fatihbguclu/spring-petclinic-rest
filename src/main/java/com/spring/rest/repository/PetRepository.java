package com.spring.rest.repository;

import com.spring.rest.model.Pet;
import com.spring.rest.model.PetType;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

public interface PetRepository {

    Collection<Pet> findAll() throws DataAccessException;

    List<PetType> findPetTypes() throws DataAccessException;

    Pet findById(int id) throws DataAccessException;

    void save(Pet pet) throws DataAccessException;

    void delete(Pet pet) throws DataAccessException;


}
