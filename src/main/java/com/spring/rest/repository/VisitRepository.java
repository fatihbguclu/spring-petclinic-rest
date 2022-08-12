package com.spring.rest.repository;

import com.spring.rest.model.Visit;
import org.springframework.dao.DataAccessException;

import java.util.Collection;
import java.util.List;

public interface VisitRepository {
    void save(Visit visit) throws DataAccessException;

    List<Visit> findByPetId(Integer petId);

    Visit findById(int id) throws DataAccessException;

    Collection<Visit> findAll() throws DataAccessException;

    void delete(Visit visit) throws DataAccessException;
}
