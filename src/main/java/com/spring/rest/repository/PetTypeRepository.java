package com.spring.rest.repository;

import com.spring.rest.model.PetType;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

public interface PetTypeRepository {
    PetType findById(int id) throws DataAccessException;

    Collection<PetType> findAll() throws DataAccessException;

    void save(PetType petType) throws DataAccessException;

    void delete(PetType petType) throws DataAccessException;
}
