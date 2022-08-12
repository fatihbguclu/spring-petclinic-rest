package com.spring.rest.repository;

import com.spring.rest.model.Owner;
import org.springframework.dao.DataAccessException;

import java.util.Collection;

public interface OwnerRepository {

    /**
     * Retrieve <code>Owner</code>s from the data store by last name, returning all owners whose last name <i>starts</i>
     * with the given name.
     *
     * @param lastName Value to search for
     * @return a <code>Collection</code> of matching <code>Owner</code>s (or an empty <code>Collection</code> if none
     * found)
     */
    Collection<Owner> findByLastName(String lastName) throws DataAccessException;

    Collection<Owner> findAll() throws DataAccessException;

    Owner findById(int id) throws DataAccessException;

    void save(Owner owner) throws DataAccessException;

    void delete(Owner owner) throws DataAccessException;

}
